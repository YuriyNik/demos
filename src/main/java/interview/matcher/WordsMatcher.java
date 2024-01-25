package interview.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Matcher module
class WordsMatcher {
    private final Map<String,Pattern> keywordPatterns= new HashMap<>();

    public WordsMatcher(List<String> keywords) {
        keywords.forEach(keyword -> this.keywordPatterns.put(keyword,Pattern.compile(Pattern.quote(keyword))));
    }

    public Map<String, List<Offset>> findMatches(String chunk, int chunkNumber) {

        Map<String, List<Offset>> matches = new HashMap<>();
        keywordPatterns.forEach((keyword,pattern) -> {
           // Pattern pattern = Pattern.compile(Pattern.quote(keyword));
            Matcher matcher = pattern.matcher(chunk);
            matcher.results()
                    .map(match -> new Offset(chunkNumber, match.start()))
                    .forEach(offset -> matches.computeIfAbsent(keyword, k -> new ArrayList<>()).add(offset));
        });
        //  System.out.println("Chunk:"+chunkNumber+" processed at separate thread;");
        return matches;
    }

    @Override
    public String toString() {
        return this.keywordPatterns.toString();
    }
}
