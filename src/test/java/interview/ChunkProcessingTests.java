package interview;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChunkProcessingTests {

    @Test
    void execute_ShouldSubmitTaskToExecutor() throws InterruptedException {
        // Arrange
        ChunkProcessing chunkProcessing = new ChunkProcessing();
        TestTask testTask = new TestTask();

        // Act
        chunkProcessing.execute(testTask);

        Thread.sleep(1000);
        // Assert
        assertTrue(testTask.isTaskExecuted());
    }

    @Test
    void complete_ShouldShutdownExecutorAndAwaitTermination() throws InterruptedException {
        // Arrange
        ChunkProcessing chunkProcessing = new ChunkProcessing();
        TestTask testTask = new TestTask();

        // Act
        chunkProcessing.execute(testTask);
        boolean result = chunkProcessing.complete();

        // Assert
        assertTrue(result);
        assertTrue(chunkProcessing.getExecutorService().isShutdown());
        assertTrue(chunkProcessing.getExecutorService().awaitTermination(10,SECONDS));
    }

    private static class TestTask implements Runnable {
        private boolean taskExecuted = false;

        @Override
        public void run() {
            // Simulate some task execution
            taskExecuted = true;
        }

        public boolean isTaskExecuted() {
            return taskExecuted;
        }
    }


}

