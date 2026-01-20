package codingJan.jobScheduler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class Task implements Delayed {
    private final Runnable runnable;
    private final long runAt;
    private int attempts = 0;
    private final int maxRetries = 3;

    public Task(Runnable runnable, long delayMillis) {
        this.runnable = runnable;
        this.runAt = System.currentTimeMillis() + delayMillis;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = runAt - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(this.runAt, ((Task) other).runAt);
    }

    public void execute() {
            runnable.run();
    }
}
