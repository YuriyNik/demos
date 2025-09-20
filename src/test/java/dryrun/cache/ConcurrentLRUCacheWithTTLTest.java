package dryrun.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentLRUCacheWithTTLTest {
        private ConcurrentLRUCacheWithTTL<String, String> cache;

        @AfterEach
        void tearDown() {
            if (cache != null) cache.stop();
        }

        @Test
        void testBasicPutGet() {
            cache = new ConcurrentLRUCacheWithTTL<>(2, 1000);
            cache.put("a", "A");
            cache.put("b", "B");

            assertEquals("A", cache.get("a"));
            assertEquals("B", cache.get("b"));
        }

        @Test
        void testEvictionLRU() {
            cache = new ConcurrentLRUCacheWithTTL<>(2, 1000);
            cache.put("a", "A");
            cache.put("b", "B");
            cache.put("c", "C"); // "a" должно вытесниться

            assertNull(cache.get("a"));
            assertEquals("B", cache.get("b"));
            assertEquals("C", cache.get("c"));
        }

        @Test
        void testTTLExpiration() throws InterruptedException {
            cache = new ConcurrentLRUCacheWithTTL<>(2, 100);
            cache.put("a", "A");

            Thread.sleep(200); // ждём истечения TTL

            assertNull(cache.get("a"), "Элемент должен быть удалён по TTL");
        }

        @Test
        void testConcurrentAccessStress() throws InterruptedException {
            cache = new ConcurrentLRUCacheWithTTL<>(100, 1000);

            int threads = 50;
            int opsPerThread = 2000;
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            AtomicBoolean failed = new AtomicBoolean(false);

            Runnable task = () -> {
                for (int i = 0; i < opsPerThread; i++) {
                    try {
                        String key = "k-" + ThreadLocalRandom.current().nextInt(500);
                        cache.put(key, "v-" + i);
                        cache.get(key);

                        // проверка capacity (через отражение или approximate)
                        if (cache.size() > 100) {
                            failed.set(true);
                            System.out.println("capacity reached - "+ cache.size());
                            break;
                        }
                    } catch (Exception e) {
                        failed.set(true);
                        System.out.println("exception raised");
                        break;
                    }
                }
            };

            for (int i = 0; i < threads; i++) {
                executor.submit(task);
            }

            executor.shutdown();
            assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
            assertFalse(failed.get(), "Cache превысил capacity или упал с ошибкой");
        }

        private int cacheSize(ConcurrentLRUCacheWithTTL<String, String> cache) {
            try {
                var field = ConcurrentLRUCacheWithTTL.class.getDeclaredField("map");
                field.setAccessible(true);
                var map = (ConcurrentMap<?, ?>) field.get(cache);
                return map.size();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
