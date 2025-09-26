package dryrun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleRaceDemo {

    private static int count=0;
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    private static AtomicInteger atomicCount = new AtomicInteger(0);

    private static AtomicInteger raceCount = new AtomicInteger(0);

    private static LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1_000_000; i++) {
            executor.submit(() -> {
                int count_int = count;
                lock.writeLock().lock();
                if (count==count_int) {
                    count = count+1;
                } else {
                    raceCount.getAndIncrement();
                    count = count+1;
                }
                lock.writeLock().unlock();
                atomicCount.getAndIncrement();
                longAdder.increment();
            });
        }

        executor.shutdown();
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
        System.out.println("count="+count);
        System.out.println("raceCount="+raceCount);
        System.out.println("atomicCount="+atomicCount);
        System.out.println("longAdder="+longAdder);
    }

}
