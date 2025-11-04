package dryrun.eventStats;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EventAggregator {
    private final Map<String, EventStats> statusMap = new ConcurrentHashMap<>();

    public EventStats getStats(String clientId){
        return statusMap.get(clientId);
    }
    public void add(Event e){
        statusMap.computeIfAbsent(e.clientId, id ->new EventStats() )
                .update(e.timestamp);
        //add logic to create EventStats for client and update internal counters
    }

    public static void main(String[] args) throws InterruptedException {
        EventAggregator aggregator = new EventAggregator();
        int threads = 10;
        int eventsPerThread = 100_000;

        ExecutorService pool = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
//            String clientId = "client" + (i % 5); // 3 clients
            pool.submit(() -> {
                for (int j = 0; j < eventsPerThread; j++) {
                    long timestamp = System.nanoTime();
                    aggregator.add(new Event("client0", "TYPE", timestamp));
                    aggregator.add(new Event("client1", "TYPE", timestamp));
                    aggregator.add(new Event("client2", "TYPE", timestamp));
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        for (int i = 0; i < 3; i++) {
            String id = "client" + i;
            EventStats stats = aggregator.getStats(id);
            if (stats != null) {
                System.out.println(id + " count=" + stats.getCount() +
                        " last=" + stats.getLastTimestamp());
            }
        }
    }

    }
