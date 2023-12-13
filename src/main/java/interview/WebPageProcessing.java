package interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class WebPageProcessing {
    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "http://norvig.com/big.txt";
        int linesPerChunk = 1000;
        // keywords dictionary
        List<String> keywords = List.of("James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas","Christopher","Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian","Ronald","Anthony","Kevin","Jason","Matthew","Gary","Timothy","Jose","Larry","Jeffrey","Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory","Joshua","Jerry","Dennis","Walter","Patrick","Peter","Harold","Douglas","Henry","Carl","Arthur","Ryan","Roger");

        WordsMatcher wordsMatcher = new WordsMatcher(keywords);

        // List to store matcher results
        List<Map<String, List<Offset>>> matcherResults = new ArrayList<>();

        //creating thread pool for exec matchers.
        ExecutorService executorService = Executors.newCachedThreadPool();

        //start the procedure of reading text by chunks size of linesPerChunk and trigger matcher for each chunk.
        //necessary to determine chunk order number
        int chunknum=0;
        //necessary to count qty of lines read for a chunk
        int lineNumber=0;
        URLConnection connection = new URL(url).openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    //start reading input text
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                        lineNumber++;
                        if (lineNumber==linesPerChunk) {
                            String chunk = stringBuilder.toString();
                            //calculate lineOffset
                            int chunkPos = chunknum*lineNumber;
                            executorService.submit(() -> {
                                Map<String, List<Offset>> matches;
                                matches = wordsMatcher.findMatches(chunk,chunkPos);
                                matcherResults.add(matches);
                            });
                            chunknum++;
                            //refresh vars for next chunk
                            lineNumber=0;
                            stringBuilder = new StringBuilder();
                        }
                    }
                    // processing the rest of lines from input - qty less  1000
                    if (stringBuilder.length() > 0) {
                        String chunk = stringBuilder.toString();
                        int chunkPos = chunknum*linesPerChunk;
                        executorService.submit(() -> {
                            Map<String, List<Offset>> matches;
                            matches = wordsMatcher.findMatches(chunk,chunkPos);
                            matcherResults.add(matches);
                        });
                    }
                    System.out.println("Waiting until processing finished....");
                    executorService.shutdown();
                    if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS))
                        System.out.print( "....matching done");
                    System.out.println();
                }

        //txt reading and matching completed
        // now run aggregation
        Aggregator aggregator = new Aggregator();
        aggregator.aggregate(matcherResults);
        aggregator.print();

    }


}
