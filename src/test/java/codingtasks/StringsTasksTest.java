package codingtasks;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StringsTasksTest {
    @Test
    public void test_getFirstEvenString(){
        assertEquals("there,",StringsTasks.getFirstEvenString("Hello there, it is a nice day"));
        assertEquals("even length word not found",StringsTasks.getFirstEvenString("Hello there it1 is1 a nice1 day"));

    }

    @Test
    public void test_isPalindrome(){
        assertTrue(StringsTasks.isPalindrome("racecar"));
        assertFalse(StringsTasks.isPalindrome("racecar1"));
        assertFalse(StringsTasks.isPalindrome(""));
        assertFalse(StringsTasks.isPalindrome(null));
    }

    @Test
    public void test_reverse() {
        assertEquals("hello funny are cats", StringsTasks.reverse("cats are funny hello"));
    }
    @Test
    public void test_countJuwerly() {
        assertEquals(3,StringsTasks.countJuwerly("stones","st"));
        assertEquals(3,StringsTasks.countJuwerly("stones","sta"));
        assertEquals(4,StringsTasks.countJuwerly("stones","sto"));
    }
    @Test
    public void test_RLEencoder(){
        assertEquals("A4B3C2XYZD4E3F3A6B28", StringsTasks.getRLE("AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB"));
        assertEquals("ABC", StringsTasks.getRLE("ABC"));
        assertEquals("", StringsTasks.getRLE(""));
    }

    @Test
    public void test_Anagram(){
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result =
                StringsTasks.getAnagram(words);
        List<List<String>> output = Arrays.asList(
                Arrays.asList("eat", "tea", "ate"),
                List.of("bat"),
                Arrays.asList("tan", "nat")
        );
        assertEquals(output,result);
        //Sample Input: [eat, tea, tan, ate, nat, bat]
        //Sample Output: [[eat, tea, ate], [bat], [tan, nat]]

    }



}