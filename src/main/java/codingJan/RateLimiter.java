package codingJan;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final int limit;
    private final long windowMillis;

    private final ConcurrentHashMap<String, Deque<Long>> localstore = new ConcurrentHashMap<>();

    public RateLimiter(int limit, long windowMillis) {
        this.limit = limit;
        this.windowMillis = windowMillis;
    }

    public boolean allow(String clientId) {
        long now = System.currentTimeMillis();
        Deque<Long> queue = localstore.computeIfAbsent(clientId, k->new ArrayDeque<>());

        synchronized (queue) {
            while (!queue.isEmpty() && queue.peekFirst() <= now - windowMillis) {
                queue.pollFirst();
            }
            if (queue.size() < limit) {
                queue.addLast(now);
                return true;
            } else {
                return false;
            }
        }



    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, 100);
        for (int i = 0; i < 100; i++) {
            System.out.println("User1 allowed=?"+ rateLimiter.allow("User1"));
            System.out.println("User2 allowed=?"+ rateLimiter.allow("User2"));
            System.out.println("User3 allowed=?"+ rateLimiter.allow("User3"));
            Thread.sleep(10);
        }
    }
}
