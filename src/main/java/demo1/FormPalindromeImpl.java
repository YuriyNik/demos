package demo1;

import java.util.HashMap;
import java.util.Map;

public class FormPalindromeImpl {

    public static boolean canFormPalindrome(String s){
        Map<Character, Integer> charsCount = new HashMap<>();
        char[] chars = s.toLowerCase().toCharArray();
        for (char ch:chars){
          charsCount.put(ch, charsCount.getOrDefault(ch,0)+1);
        }
        System.out.println(charsCount);
        int oddCount = 0;
        for (int count : charsCount.values()) {
            if (count % 2 != 0) {
                oddCount++;
                if (oddCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(FormPalindromeImpl.canFormPalindrome("carrace")); // можно “racecar”
        System.out.println(FormPalindromeImpl.canFormPalindrome("aabb"));    // “abba”
        System.out.println(FormPalindromeImpl.canFormPalindrome("abc"));    // не может
        System.out.println(FormPalindromeImpl.canFormPalindrome(""));        // пустая строка считается
        System.out.println(FormPalindromeImpl.canFormPalindrome("AaBb"));

    }
}
