package codingJan.jobScheduler;

interface Scheduler {
    void schedule(Runnable task, long delayMillis);
}
