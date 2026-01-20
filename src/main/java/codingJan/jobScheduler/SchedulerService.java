package codingJan.jobScheduler;

import java.util.concurrent.DelayQueue;

public class SchedulerService implements Scheduler{
    DelayQueue<Task> queue = new DelayQueue<>();

    public SchedulerService() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    Task task = queue.take(); // blocks until ready
                    task.execute();
                } catch (Exception e) {
//                    if (task.attempts++ < task.maxRetries) {
//                        long backoff = calculateBackoff(task.attempts);
//                        reschedule(task, backoff);
//                    }
                    //need to support retry flow here
                    break;
                }
            }
        });
        worker.start();
    }

    public void schedule(Runnable task, long delayMillis) {
        queue.offer(new Task(task, delayMillis));
    }

}
