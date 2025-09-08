package dryrun;

import java.util.*;
import java.util.stream.Collectors;
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

    public String reverseWords(String s) {
        String[] words = s.split(" ");
        StringBuilder result= new StringBuilder();
//        for (int i = words.length-1; i >=0 ; i--) {
//            result.append(words[i]);
//            if (i>0) {result.append(" ");}
//        }
//        return result.toString();
//        Collections.reverse(Arrays.asList(words));
//        return String.join(" ",words);
        return IntStream.range(0, words.length)
                .mapToObj(i->words[words.length-i-1])
                .collect(Collectors.joining(" "));
    }

    public String rleEncode(String s) {
        if (!s.matches("[A-Z]*")) {
            throw  new IllegalArgumentException();
        }
        if (!s.isEmpty()){
        StringBuilder result = new StringBuilder();
        int count = 1;
        char currCh = s.charAt(0);
            for (int i = 1; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (currCh==ch){
                    count++;
                } else {
                    result.append(currCh);
                    if (count>1){
                        result.append(count);
                    }
                    currCh=ch;
                    count=1;
                }

            }
            result.append(currCh);
            if (count>1){
                result.append(count);
            }
        return result.toString();
        }

        return "";
    }

    public List<List<String>> groupAnagrams(String[] strs){

        Map<String, List<String>> result = new HashMap<>();
        for (String word:strs) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            result.computeIfAbsent(key, k->new ArrayList<>()).add(word);
        }

        return result.values().stream().toList();
    }
//task 11
    public boolean isOneEditAway(String s1, String s2) {
        return false;
    }
//task 13
    public int numJewelsInStones(String jewels, String stones){
        return 0;
    }
//task 17
    public int areAnagrams(String s1, String s2){
        return 0;
    }



}
