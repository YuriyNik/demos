package dryrun.ratelimit;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowRateLimiter implements RateLimiter{

    private final long windowSizeMillis;
    private final int maxRequests;

    private final Deque<Long> timestamps = new ArrayDeque<>();
    private final ReentrantLock lock = new ReentrantLock();

    public SlidingWindowRateLimiter(long windowSizeMillis, int maxRequests){
        this.windowSizeMillis=windowSizeMillis;
        this.maxRequests=maxRequests;
    }

    @Override
    public boolean allowRequest(){
        long now = System.currentTimeMillis();
        lock.lock();
        try {
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() >= windowSizeMillis) {
                System.out.println(" timestamps.removeFirst()" + timestamps.peekFirst());
                timestamps.removeFirst();
            }
            if (timestamps.size() < maxRequests) {
                timestamps.addLast(now);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }


    }

}
