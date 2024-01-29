package codingtasks;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringsTasksTest {
    @Test
    public void test_getFirstEvenString(){
        assertEquals("there",StringsTasks.getFirstEvenString("Hello there, it is a nice day"));
    }

    @Test
    public void test_isPalindrome(){
        assertTrue(StringsTasks.isPalindrome("racecar"));
    }

    @Test
    public void test_reverse() {
        assertEquals("funny are cats", StringsTasks.reverse("cats are funny"));
    }
    @Test
    public void test_countJuwerly() {
        assertEquals(3,StringsTasks.countJuwerly("stones","st"));
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
        List<List<String>> result = StringsTasks.getAnagram(words);
        //Sample Input: [eat, tea, tan, ate, nat, bat]
        //Sample Output: [[eat, tea, ate], [bat], [tan, nat]]

    }



}