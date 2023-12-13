package interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class WebPageProcessing {
    private final List<Map<String, List<Offset>>> matcherResults = new ArrayList<>();
    public WebPageProcessing(){
    }

    public List<Map<String, List<Offset>>> getMatcherResults() {
        return matcherResults;
    }

    public void startProcessing(String url, int linesPerChunk, List<String> keywords ) throws IOException, InterruptedException {
        WordsMatcher wordsMatcher = new WordsMatcher(keywords);
        // List to store matcher results
        ChunkProcessing chunkProcessing = new ChunkProcessing();
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
                    chunkProcessing.execute(() -> matcherResults.add(wordsMatcher.findMatches(chunk,chunkPos)));
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
                chunkProcessing.execute(() -> matcherResults.add(wordsMatcher.findMatches(chunk,chunkPos)));
            }
            System.out.println("Waiting until processing finished....");
            chunkProcessing.complete();
            System.out.println( "....matching done");
        }
    }

    public void runAggregationAndPrint() {
        Aggregator aggregator = new Aggregator();
        aggregator.aggregate(this.getMatcherResults());
        aggregator.print();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        WebPageProcessing webPageProcessing = new WebPageProcessing();
        webPageProcessing.startProcessing("http://norvig.com/big.txt",
         1000,
         List.of("James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas","Christopher","Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian","Ronald","Anthony","Kevin","Jason","Matthew","Gary","Timothy","Jose","Larry","Jeffrey","Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory","Joshua","Jerry","Dennis","Walter","Patrick","Peter","Harold","Douglas","Henry","Carl","Arthur","Ryan","Roger"));

        //txt reading and matching completed
        // now run aggregation
        webPageProcessing.runAggregationAndPrint();

    }


}
