package dryrun.queue;

import org.junit.jupiter.api.Test;

public class BlockingQueueTest {

    @Test
    void concurrent_Test() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueueSynchronized<>(5);

        Runnable producer = ()-> {
            try {
                System.out.println(Thread.currentThread().getName()+" started");
            for (int i = 1; i < 10; i++) {
                queue.put(i);
                System.out.println("Produced value "+i+" by "+Thread.currentThread().getName());
//                Thread.sleep(10);
            }
            } catch (InterruptedException e) {
                System.out.println("Exception"+e.getCause());
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () -> {
            try{
                System.out.println(Thread.currentThread().getName()+" started");
                for (int i = 1; i < 10; i++) {
                    int val = queue.take();
                    System.out.println("Consumed "+val+" by"+Thread.currentThread().getName());
                    Thread.sleep(300);
                }
                System.out.println("completed");
            }catch (InterruptedException e){
                System.out.println("Exception"+e.getCause());
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer, "PRODUCER-1").start();
        new Thread(consumer, "CONSUMER-1").start();
        new Thread(consumer, "CONSUMER-2").start();
        Thread.sleep(1000);
    }
}
