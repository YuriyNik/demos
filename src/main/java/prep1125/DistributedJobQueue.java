package prep1125;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class DistributedJobQueue {

    // Represents a task/job for processing
    static class Job {
        final String id;
        final String payload;
        Job(String id, String payload) {
            this.id = id;
            this.payload = payload;
        }
    }

    // Job currently being processed and waiting for ACK
    static class InFlightJob {
        final Job job;
        final long expireTime; // ack timeout

        InFlightJob(Job job, long timeoutMs) {
            this.job = job;
            this.expireTime = System.currentTimeMillis() + timeoutMs;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final Map<String, InFlightJob> processing = new ConcurrentHashMap<>();
    private final long ackTimeoutMs;
    private final ScheduledExecutorService requeueWorker =
            Executors.newSingleThreadScheduledExecutor();

    public DistributedJobQueue(long ackTimeoutMs) {
        this.ackTimeoutMs = ackTimeoutMs;
        startRequeueDaemon();
    }

    // Producer API
    public void publish(String payload) {
        String id = UUID.randomUUID().toString();
        queue.offer(new Job(id, payload));
    }

    // Consumer pulls job
    public Job poll() {
        Job job = queue.poll();
        if (job != null) {
            processing.put(job.id, new InFlightJob(job, ackTimeoutMs));
        }
        return job;
    }

    // Consumer ACK
    public void ack(String jobId) {
        processing.remove(jobId);
    }

    // Requeue if not ACK-ed in time
    private void startRequeueDaemon() {
        requeueWorker.scheduleAtFixedRate(() -> {
            for (Iterator<Map.Entry<String, InFlightJob>> it = processing.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, InFlightJob> entry = it.next();
                if (entry.getValue().isExpired()) {
                    System.out.println("⚠️ Requeue " + entry.getKey());
                    queue.offer(entry.getValue().job);
                    it.remove();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        requeueWorker.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        DistributedJobQueue broker = new DistributedJobQueue(3000);

        // Producer
        broker.publish("Task-A");
        broker.publish("Task-B");

        // Consumer (Worker)
        Job job = broker.poll();
        if (job != null) {
            // simulate work
            System.out.println("Worker got: " + job.payload);
            Thread.sleep(1000);
         //   broker.ack(job.id); // try commenting this to see retry
        }
    }
}
