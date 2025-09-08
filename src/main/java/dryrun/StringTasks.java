package dryrun;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class StringTasks {
    public String firstEvenLengthWord(String s){
        String[] words = s.split("\\s");
//        for (String word:words){
//            if (word.length()%2==0)
//            {
//                return word;
//            }
//        }
//        return "";
        return Arrays.stream(words).filter(w -> w.length()%2==0 ).findFirst().orElse("");
    }
    public String longestWord(String s){
        String[] words = s.split("\\s");
//        String longest = "";
//        for (String word:words){
//            if (word.length()>longest.length())
//            {
//                longest=word;
//            }
//        }

        return Arrays.stream(words).max(Comparator.comparingInt(String::length)).orElse("");
    }

    public boolean isPalindrome(String s){
        String input = s.replaceAll("\\s","").toLowerCase();
// Option 1
        int length = input.length();
//        for (int i = 0; i < length/2; i++) {
//            if (input.charAt(i)!=input.charAt(length-1-i)){
//                return false;
//            }
//        }
//        return true;
// Option 2 with reverse
//    String reverse = new StringBuilder(input).reverse().toString();
//    return input.equals(reverse);
       return IntStream.range(0,length/2)
               .allMatch(i -> input.charAt(i)==input.charAt(length-1-i));
    }

}
