package dryrun.demo1;

import demo1.RateLimiter;
import demo1.RateLimiterImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterTest {
    @Test
    void rateLimiter_allowRequest_Test() throws InterruptedException {
        RateLimiter rl = new RateLimiterImpl(2, 1000);
        assertTrue(rl.allowRequest("A"));
        assertTrue(rl.allowRequest("A"));
        assertFalse(rl.allowRequest("A")); // третий запрос — отказ, пока не истечёт time window
        Thread.sleep(1001);
        assertTrue(rl.allowRequest("A")); // после окна снова допускается
// разные клиенты независимы
        assertTrue(rl.allowRequest("B"));
        assertTrue(rl.allowRequest("B"));
        assertFalse(rl.allowRequest("B"));
    }
}
