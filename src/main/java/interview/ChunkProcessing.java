package interview;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ChunkProcessing {
    //creating thread pool for exec matchers.
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void execute(Runnable task){
        executorService.submit(task);
    }

    public boolean complete() throws InterruptedException {
        executorService.shutdown();
        return executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
