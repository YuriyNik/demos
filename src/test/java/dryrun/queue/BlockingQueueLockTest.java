package dryrun.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockingQueueLockTest {

    @Test
    void blockingQueueLock_Tests() throws InterruptedException, ExecutionException {

        BlockingQueue<Integer> queue = new BlockingQueueLock<>(5);
        final int itemsCount = 10;

        AtomicInteger producedCount = new AtomicInteger(0);
        AtomicInteger consumedCount = new AtomicInteger(0);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            final int producerId = i;
            futures.add(executorService.submit(() -> {
                for (int j = 0; j < itemsCount; j++) {
                    try {
                        queue.put(j);
                        producedCount.incrementAndGet();
                        System.out.println("Producer#"+producerId+" produced Value="+j);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }) );
        }

        for (int i = 0; i < 3; i++) {
            final int consumerId = i;
            futures.add(executorService.submit(() -> {
                for (int j = 0; j < itemsCount; j++) {
                    try {
                        int taken = queue.take();
                        consumedCount.incrementAndGet();
                        System.out.println("Consumer#"+consumerId+" consumed Value="+taken);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }) );
        }

        for (Future<?> task:futures) {
            task.get();
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        System.out.println("Test completed, queue size="+queue.size());
        assertEquals(0, queue.size(),"Queue must be empty after all operations");
        assertEquals(itemsCount*3, producedCount.get(), "all produced");
        assertEquals(itemsCount*3, consumedCount.get(), "all consumed");

    }

    @Test
    void blockingQueueLock_Test_Threads() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueueLock<>(5);

        Runnable producer = () -> {
            try {
                for (int i = 1; i < 10; i++) {
                    queue.put(i);
                    System.out.println(Thread.currentThread().getName() + " produced " + i);
                    Thread.sleep(15);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () -> {
            try {
                for (int i = 1; i < 10; i++) {
                    int val = queue.take();
                    System.out.println(Thread.currentThread().getName() + " consumed " + val);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer, "Producer-1").start();
        new Thread(producer, "Producer-2").start();
        new Thread(consumer, "Consumer-1").start();
        new Thread(consumer, "Consumer-2").start();
        Thread.sleep(1000);
        assertEquals(0, queue.size(),"Queue must be empty after all operations");


    }

}
