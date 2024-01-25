package interview.matcher;

import interview.matcher.Offset;
import interview.matcher.WordsMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordMatcherTests {

    @Test
    void testFindMatches() {
        List<String> keywords = List.of("Java", "programming", "example");
        WordsMatcher wordsMatcher = new WordsMatcher(keywords);

        String chunk = "Java is a programming language. This is an example.";
        int chunkNumber = 1;

        Map<String, List<Offset>> matches = wordsMatcher.findMatches(chunk, chunkNumber);

        // Проверяем, что ожидаемые ключевые слова присутствуют в результатах
        assertEquals(1, matches.get("Java").size());
        assertEquals(1, matches.get("programming").size());
        assertEquals(1, matches.get("example").size());

        // Проверяем, что смещения соответствуют ожидаемым значениям
        assertEquals(new Offset(chunkNumber, 0), matches.get("Java").get(0));
        assertEquals(new Offset(chunkNumber, 10), matches.get("programming").get(0));
        assertEquals(new Offset(chunkNumber, 43), matches.get("example").get(0));
    }
    @Test
    void testKeywords(){
     //   List<String> keywords = List.of("James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas","Christopher","Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian","Ronald","Anthony","Kevin","Jason","Matthew","Gary","Timothy","Jose","Larry","Jeffrey","Frank","Scott","Eric","Stephen","Andrew","Raymond","Gregory","Joshua","Jerry","Dennis","Walter","Patrick","Peter","Harold","Douglas","Henry","Carl","Arthur","Ryan","Roger");
     //   WordsMatcher wordsMatcher = new WordsMatcher(keywords);
      //  System.out.println(wordsMatcher);

    }


}
