package dryrun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HeavyThreadsDemo {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1_000_000; i++) {
            int taskId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(1000); // блокирующий вызов
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (taskId % 1000 == 0) {
                    System.out.println("Done " + taskId);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);
    }
}
