package dryrun.eventStats;

import java.util.concurrent.atomic.AtomicLong;

public class EventStats {
    private final AtomicLong count = new AtomicLong(0);
    private final AtomicLong lastTimestamp = new AtomicLong(0);

    public void update(long timestamp){
        count.incrementAndGet();
        lastTimestamp.updateAndGet( prev -> Math.max(prev,timestamp));
    }

    public long getCount() {
        return count.get();
    }

    public long getLastTimestamp() {
        return lastTimestamp.get();
    }

    @Override
    public String toString(){
        return "EventStats{count=" + count + ", lastTimestamp=" + lastTimestamp + '}';
    }
}

