package dryrun.ratelimit;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RateLimiterFixedWindowService implements RateLimiter{

    private final long windowSizeMillis;
    private AtomicLong windowStart;
    private final int maxRequests;
    private AtomicInteger counter;

    RateLimiterFixedWindowService(long windowSizeMillis, int maxRequests){
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = new AtomicLong(System.currentTimeMillis());
        this.maxRequests = maxRequests;
        this.counter = new AtomicInteger(0);
    }

    @Override
    public boolean allowRequest(){
    long now = System.currentTimeMillis();
    long currentWindow = windowStart.get();
     if ((now-currentWindow)>=windowSizeMillis){
         if(windowStart.compareAndSet(currentWindow, now))
         {
             counter.set(0);
         } //retries to be implemented here
     }
     int currCount = counter.incrementAndGet();
     return currCount<=maxRequests;
    }

}
