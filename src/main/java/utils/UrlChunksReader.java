package utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

///not ready draft version

public class UrlChunksReader implements Iterator<String> {

    private BufferedReader br;
    private String line;

    public UrlChunksReader(String url, int linesPerChunk) throws IOException {
         url = "http://norvig.com/big.txt";
         linesPerChunk = 1000;
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

            }

        }
    }

    @Override
    public boolean hasNext() {
        try {
            line = br.readLine();
            return line != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String next() {
        return line;
    }
}