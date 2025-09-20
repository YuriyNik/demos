package dryrun.ratelimit;

import java.util.concurrent.atomic.AtomicLong;

public class RateLimiterTokenBucketService implements RateLimiter{
    private final long capacity; // максимальное количество токенов
    private final long refillRate; // токенов в секунду
    private AtomicLong availableTokens;
    private volatile long lastRefillTimestamp;

    public RateLimiterTokenBucketService(long capacity, long refillRate){
        this.capacity=capacity;
        this.refillRate = refillRate;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestamp = System.nanoTime();
    }


    @Override
    public boolean allowRequest(){
        refill();

        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedNanos = now - lastRefillTimestamp;
        long tokensToAdd = (elapsedNanos * refillRate) / 1_000_000_000L;

        if (tokensToAdd > 0) {
            long newTokens = Math.min(capacity, availableTokens.get() + tokensToAdd);
            availableTokens.set(newTokens);
            lastRefillTimestamp = now;
        }
    }

}
