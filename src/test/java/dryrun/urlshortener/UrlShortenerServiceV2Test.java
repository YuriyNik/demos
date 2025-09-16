package dryrun.urlshortener;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlShortenerServiceV2Test {


    @Test
    void urlShortenerServiceV2_Tests() throws ExecutionException, InterruptedException {
        UrlShortenerServiceV2 urlShortenerServiceV2 = new UrlShortenerServiceV2();

        String url = "https://amdocsglobal.udemy.com/course/the-complete-microservices-event-driven-architecture/";

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<String> task = () -> urlShortenerServiceV2.shorten(url);

        Future<String> [] futures = new Future[100];
        for (int i = 0; i < 100; i++) {
            futures[i] = executorService.submit(task);
        }

        String firstResult = null;
        for (Future<String> future:futures ){
            String code = future.get();
            if(firstResult==null) {
                firstResult=code;
            }
            System.out.println("Codes = "+firstResult + " "+code);
            assertEquals(firstResult,code);
        }
        executorService.shutdown();
        executorService.awaitTermination(100,TimeUnit.MILLISECONDS);
    }

}
