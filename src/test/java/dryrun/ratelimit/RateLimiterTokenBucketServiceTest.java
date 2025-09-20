package dryrun.ratelimit;

import org.junit.jupiter.api.Test;

public class RateLimiterTokenBucketServiceTest {

    @Test
    void rateLimiterTokenBucketService_Test() throws InterruptedException {
        RateLimiter limiter = new RateLimiterTokenBucketService(
                10, 10
        );
        for (int i = 1; i <= 11; i++) {
            System.out.println("Request "+i+" allowed?-"+limiter.allowRequest()+";"+System.currentTimeMillis());
        }
        Thread.sleep(1000);
        for (int i = 1; i <= 11; i++) {
            System.out.println("Request "+i+" allowed?-"+limiter.allowRequest()+";"+System.currentTimeMillis());
        }
        Thread.sleep(1000);
        for (int i = 1; i <= 11; i++) {
            System.out.println("Request "+i+" allowed?-"+limiter.allowRequest()+";"+System.currentTimeMillis());
        }

    }

}
