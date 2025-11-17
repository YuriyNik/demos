package prep1125;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryableJobQueue {

    private final BlockingQueue<JobWrapper> queue = new LinkedBlockingQueue<>();
    private final ExecutorService workers;
    private final int maxRetries;
    private final AtomicInteger activeJobs = new AtomicInteger(0);
    private volatile boolean running = true;

    private static class JobWrapper {
        final Runnable job;
        int retries;
        JobWrapper(Runnable job, int retries) {
            this.job = job;
            this.retries = retries;
        }
    }

    public RetryableJobQueue(int workerCount, int maxRetries) {
        this.workers = Executors.newFixedThreadPool(workerCount);
        this.maxRetries = maxRetries;
        startWorkers();
    }

    private void startWorkers() {
        for (int i = 0; i < ((ThreadPoolExecutor) workers).getCorePoolSize(); i++) {
            workers.submit(() -> {
                while (running || !queue.isEmpty()) {
                    try {
                        JobWrapper wrapper = queue.poll(100, TimeUnit.MILLISECONDS);
                        if (wrapper == null) continue;
                        activeJobs.incrementAndGet();
                        try {
                            wrapper.job.run();
                        } catch (Exception e) {
                            if (wrapper.retries < maxRetries) {
                                wrapper.retries++;
                                queue.offer(wrapper);
                            } else {
                                System.err.println("Job failed permanently after retries");
                            }
                        } finally {
                            activeJobs.decrementAndGet();
                        }
                    } catch (InterruptedException ignored) {}
                }
            });
        }
    }

    public void submit(Runnable job) {
        if (!running) throw new IllegalStateException("Queue is shutting down");
        queue.offer(new JobWrapper(job, 0));
    }

    public void shutdown() {
        running = false;
        workers.shutdown();
    }

    public int getActiveJobs() {
        return activeJobs.get();
    }

    public static void main(String[] args) throws InterruptedException {
        RetryableJobQueue queue = new RetryableJobQueue(3, 2);

        for (int i = 0; i < 10; i++) {
            int id = i;
            queue.submit(() -> {
                if (id % 2 > 0)
                    throw new RuntimeException("Random fail in job " + id);
                System.out.println("✅ Completed job " + id);
            });
        }

        Thread.sleep(3000);
        queue.shutdown();
        System.out.println("Active jobs: " + queue.getActiveJobs());
    }
}
