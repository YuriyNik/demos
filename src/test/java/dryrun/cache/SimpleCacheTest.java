package dryrun.cache;

import org.junit.jupiter.api.Test;

public class SimpleCacheTest {

    @Test
    void simpleCache_Test() throws InterruptedException {
        Cache<Integer,String> cache = new SimpleCache<>(100);

        for (int i = 0; i < 100; i++) {
            cache.put(i,"config "+i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(cache.get(i));
        }
        Thread.sleep(100);
        System.out.println("cache size="+cache.size());
        for (int i = 0; i < 100; i++) {
            System.out.print(cache.get(i)+";");
            System.out.print("cache size="+cache.size());
        }
        System.out.println("cache size=");
        System.out.println(cache.size());

    }
}
