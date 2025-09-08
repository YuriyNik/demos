package dryrun;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringTasksTest {

    @Test
    void firstEvenLengthWord_tests(){
        StringTasks stringTasks = new StringTasks();
        assertEquals(stringTasks.firstEvenLengthWord("Java is cool"),"Java");
        assertEquals(stringTasks.firstEvenLengthWord("I love coding"),"love");
        assertEquals(stringTasks.firstEvenLengthWord("Odd word here"),"word");
        assertEquals(stringTasks.firstEvenLengthWord("one three seven"),"");
    }

    @Test
    void longestWord_tests(){
        StringTasks stringTasks = new StringTasks();
        assertEquals("coding", stringTasks.longestWord("I love coding"));
        assertEquals("hello",  stringTasks.longestWord("hi hello hey"));
        assertEquals("Java",   stringTasks.longestWord("Java is cool"));
        assertEquals("example",stringTasks.longestWord("this is an example"));
    }

    @Test
    void isPalindrome_test(){
        StringTasks stringTasks = new StringTasks();
        assertTrue(stringTasks.isPalindrome("madam"));
        assertTrue(stringTasks.isPalindrome("A man a plan a canal Panama"));
        assertTrue(stringTasks.isPalindrome("No lemon no melon"));
        assertFalse(stringTasks.isPalindrome("hello"));
        assertFalse(stringTasks.isPalindrome("Java"));
    }

    @Test
    void reverseWords_test(){
        StringTasks stringTasks = new StringTasks();
        assertEquals("funny are cats", stringTasks.reverseWords("cats are funny"));
        assertEquals("world hello", stringTasks.reverseWords("hello world"));
        assertEquals("one", stringTasks.reverseWords("one"));
        assertEquals("", stringTasks.reverseWords(""));

    }

    @Test
    void rleEncode_test(){
        StringTasks stringTasks = new StringTasks();
        assertEquals("A4B3C2XYZD4E3F3A6B28",
                stringTasks.rleEncode("AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB"));
        assertEquals("A", stringTasks.rleEncode("A"));
        assertEquals("AB", stringTasks.rleEncode("AB"));
        assertEquals("", stringTasks.rleEncode(""));
        assertThrows(IllegalArgumentException.class, () -> stringTasks.rleEncode("A1B")); // невалидный символ
        assertThrows(IllegalArgumentException.class, () -> stringTasks.rleEncode("abc")); // невалидный символ

    }
    @Test
    void groupAnagrams_test(){
        StringTasks stringTasks = new StringTasks();
        assertEquals(
                List.of(
                        List.of("eat","tea","ate"),
                        List.of("tan","nat"),
                        List.of("bat")
                ),
                stringTasks.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"})
        );

        assertEquals(
                List.of(List.of("a")),
                stringTasks.groupAnagrams(new String[]{"a"})
        );

        assertEquals(
                List.of(List.of("")),
                stringTasks.groupAnagrams(new String[]{""})
        );

    }



}
