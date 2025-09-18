package dryrun.ratelimit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlidingWindowRateLimiterTest {

    @Test
    void slidingWindowRateLimiter_Test() throws InterruptedException {
        RateLimiter limiter = new SlidingWindowRateLimiter(100,10);

        for (int i = 1; i <= 15; i++) {
            System.out.println("allowRequest "+i +" "+limiter.allowRequest()+";"+System.currentTimeMillis());
        }
        Thread.sleep(100);
        for (int i = 1; i <= 15; i++) {
            System.out.println("allowRequest "+i +" "+limiter.allowRequest()+";"+System.currentTimeMillis());
        }
    }


    @Test
    void testConcurrency() throws ExecutionException, InterruptedException {
        int threads = 50;
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        RateLimiter limiter = new SlidingWindowRateLimiter(100,10);
        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            futures.add(exec.submit(limiter::allowRequest));
        }
        exec.shutdown();
        for (Future<Boolean> future:futures) {
            future.get();
        }

        long passed = futures.stream().filter( f -> {
                    try{ return f.get(); } catch (Exception e) {return false;}
        }
        ).count();
        assertTrue(passed<=10);
    }

}
