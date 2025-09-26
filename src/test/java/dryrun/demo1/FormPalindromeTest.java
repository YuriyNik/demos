package dryrun.demo1;

import demo1.FormPalindromeImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormPalindromeTest {
    @Test
    void FormPalindrome_Test(){
        assertTrue(FormPalindromeImpl.canFormPalindrome("carrace")); // можно “racecar”
        assertTrue(FormPalindromeImpl.canFormPalindrome("aabb"));    // “abba”
        assertFalse(FormPalindromeImpl.canFormPalindrome("abc"));    // не может
        assertTrue(FormPalindromeImpl.canFormPalindrome(""));        // пустая строка считается
        assertTrue(FormPalindromeImpl.canFormPalindrome("AaBb"));    // регистр игнорируется
    }
}
