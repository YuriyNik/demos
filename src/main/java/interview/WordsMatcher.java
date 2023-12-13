package interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Matcher module
class WordsMatcher {
    private final List<String> keywords;

    public WordsMatcher(List<String> keywords) {
        this.keywords = keywords;
    }

    public Map<String, List<Offset>> findMatches(String chunk, int chunkNumber) {

        Map<String, List<Offset>> matches = new HashMap<>();

        keywords.forEach(keyword -> {
            Pattern pattern = Pattern.compile(Pattern.quote(keyword));
            Matcher matcher = pattern.matcher(chunk);

            matcher.results()
                    .map(match -> new Offset(chunkNumber, match.start()))
                    .forEach(offset -> matches.computeIfAbsent(keyword, k -> new ArrayList<>()).add(offset));
        });
        //  System.out.println("Chunk:"+chunkNumber+" processed at separate thread;");
        return matches;
    }
}
