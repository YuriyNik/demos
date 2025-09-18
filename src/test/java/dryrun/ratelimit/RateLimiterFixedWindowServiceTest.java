package dryrun.ratelimit;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterFixedWindowServiceTest {

    @Test
    void concurrecyTest() throws InterruptedException {
        int threads = 20;
        RateLimiter limiter = new RateLimiterFixedWindowService(1000, 10);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
//        CountDownLatch latch = new CountDownLatch(threads);

        final int[] passed = {0};
        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                if (limiter.allowRequest()) {
                    synchronized (passed) {
                        passed[0]++;
                    }
                }
//                latch.countDown();
            });
        }

//        latch.await();
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);

        // не больше 10 должны пройти
        assertTrue(passed[0] <= 10);
    }


    @Test
    void allowRequest_Test() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiterFixedWindowService(
              10,
              5
        );
        Thread.sleep(1);
        for (int i = 0; i < 300; i++) {
            System.out.println("Request "+i+" allowed?-"+rateLimiter.allowRequest()+";"+System.currentTimeMillis());
            Thread.sleep(1);
        }

    }
}
