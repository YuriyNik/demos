package demo1;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterImpl implements RateLimiter{
    private final int maxRequests;
    private final long timeWindowMillis;


    Map<String, ClientBucket> clientTimeStamps = new ConcurrentHashMap<>();


    private class ClientBucket{
        private final Deque<Long> timeStamps = new ConcurrentLinkedDeque<>();
        private final AtomicInteger count = new AtomicInteger(0);
        void add(Long timems){
            timeStamps.addLast(timems);
            count.incrementAndGet();
        }
        void cleanup(Long now){
            while (!timeStamps.isEmpty() && (now - timeStamps.peekFirst()) >= timeWindowMillis) {
                timeStamps.pollFirst();
                count.decrementAndGet();
            }
        }
    }

    public RateLimiterImpl(int maxRequests, long timeWindowMillis){
        this.maxRequests=maxRequests;
        this.timeWindowMillis=timeWindowMillis;
    }

    @Override
    public boolean allowRequest(String clientId){
        ClientBucket cb = clientTimeStamps.computeIfAbsent(clientId, bucket -> new ClientBucket() );
        Long now = System.currentTimeMillis();
        cb.cleanup(now);
         if (cb.count.get()<maxRequests) {
             cb.add(now);
             return true;
         } else {
             return false;
         }
    }

}
