package dryrun;

import java.util.Arrays;
import java.util.Comparator;

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
        return false;
    }

}
