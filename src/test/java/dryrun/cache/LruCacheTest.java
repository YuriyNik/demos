package dryrun.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class LruCacheTest {
    @Test
    void lruCacheTest(){
        Cache<Integer, String> cache = new LruCache<>(100, 100);
        for (int i = 0; i < 200 ; i++) {
            cache.put(i,"value "+i);
        }
        System.out.println(cache.size());

    }

    private Cache<String, String> cache;

    @AfterEach
    void tearDown() {
        if (cache != null) {
            cache.stop(); // корректно завершаем cleaner
        }
    }

    @Test
    void testBasicPutGet() {
        cache = new LruCache<>(2, 1000);
        cache.put("a", "A");
        cache.put("b", "B");

        assertEquals("A", cache.get("a"));
        assertEquals("B", cache.get("b"));
    }

    @Test
    void testEvictionLRU() {
        cache = new LruCache<>(2, 1000);
        cache.put("a", "A");
        cache.put("b", "B");
        cache.put("c", "C"); // должно вытеснить "a"

        assertNull(cache.get("a"));
        assertEquals("B", cache.get("b"));
        assertEquals("C", cache.get("c"));
    }

    @Test
    void testTTLExpiration() throws InterruptedException {
        cache = new LruCache<>(2, 100);
        cache.put("a", "A");

        Thread.sleep(200); // ждём, пока TTL истечёт

        assertNull(cache.get("a"), "Элемент должен быть удалён по TTL");
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        cache = new LruCache<>(100, 1000);

        int threads = 10;
        int opsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        Runnable writer = () -> {
            for (int i = 0; i < opsPerThread; i++) {
                cache.put("key-" + i, "val-" + i);
            }
        };

        Runnable reader = () -> {
            for (int i = 0; i < opsPerThread; i++) {
                cache.get("key-" + i);
            }
        };

        for (int t = 0; t < threads; t++) {
            executor.submit(t % 2 == 0 ? writer : reader);
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));

        // Проверим, что часть данных точно сохранилась
        assertNotNull(cache.get("key-10"));
    }
//    @Test
//    void stressTestCapacity() throws InterruptedException {
//        int capacity = 50;
//        Cache<String, String> cache = new LruCache<>(capacity, 5000);
//
//        int threads = 100;
//        int opsPerThread = 5000;
//        ExecutorService executor = Executors.newFixedThreadPool(threads);
//
//        AtomicBoolean failed = new AtomicBoolean(false);
//
//        Runnable task = () -> {
//            for (int i = 0; i < opsPerThread; i++) {
//                try {
//                    String key = "k-" + ThreadLocalRandom.current().nextInt(10_000);
//                    cache.put(key, "v-" + i);
//                    cache.get(key);
//                    // проверка capacity
//                    if (cacheSize(cache) > capacity) {
//                        failed.set(true);
//                        break;
//                    }
//                } catch (Exception e) {
//                    failed.set(true);
//                    break;
//                }
//            }
//        };
//
//        for (int i = 0; i < threads; i++) {
//            executor.submit(task);
//        }
//
//        executor.shutdown();
//        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
//
//        assertFalse(failed.get(), "Кэш превысил capacity или упал с ошибкой");
//        cache.stop();
//    }
//
//    // helper: отражение приватного поля map
//    private int cacheSize(Cache<String, String> cache) {
//        try {
//            var field = LruCache.class.getDeclaredField("map");
//            field.setAccessible(true);
//            var map = (ConcurrentMap<?, ?>) field.get(cache);
//            return map.size();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }



}
