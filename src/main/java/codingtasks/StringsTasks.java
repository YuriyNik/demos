package codingtasks;

import java.util.*;

public class StringsTasks {
    public static String getFirstEvenString(String sentence){
        String[] words = sentence.split("\\s");
        for (String word:words) {
            if (word.length()%2==0){
                return word;
            }
        }
        return "even length word not found";
    }

    public static boolean isPalindrome(String input) {
        if ((input==null)|(Objects.equals(input, ""))) return false;
        String cleanInput = input.replaceAll("\\s", "").toLowerCase();
        int length = cleanInput.length();
        for (int i = 0; i < length/2; i++) {
            if (cleanInput.charAt(i)!=cleanInput.charAt(length-1-i))
                return false;
        }

        return true;
    }

    public static String reverse(String input){
        String[] words = input.split("\\s");
        StringBuilder reverseOrder = new StringBuilder();
        for (int i = words.length-1; i >=0 ; i--) {
            reverseOrder.append(words[i]).append(" ");
        }
        return reverseOrder.toString().trim();
    }

    public static int countJuwerly(String stones, String juwerly) {
        char[] stoneChars = stones.toCharArray();
        int qty = 0;
        for (char stoneChar : stoneChars) {
            if (juwerly.indexOf(stoneChar) != -1) {
                qty++;
            }
        }
        return qty;
    }
    public static List<List<String>> getAnagram(String[] words) {
        Map<String , List<String>> anagram = new HashMap<>();
        for(String word:words){
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            String sortedWord= new String(charArray);
            anagram.computeIfAbsent(sortedWord,k->new ArrayList<>()).add(word);
        }
    return anagram.values().stream().toList();
    }

    public static String getRLE(String input){
        if ((Objects.equals(input, ""))|(input==null)) return "";
        StringBuilder output = new StringBuilder();
        int charqty=1;
        char current;
        char prev=input.charAt(0);
        output.append(prev);
        for (int i = 1; i < input.length(); i++) {
            current = input.charAt(i);
            if (prev==current) {
                charqty++;
            } else {
                if (charqty>1) output.append(charqty);
                charqty=1;
                output.append(current);
                prev = current;
            }
        }
        if (charqty>1) output.append((charqty));

        return output.toString();
    }


    public static boolean canTransformWithOneEdit(String original, String target){
        if (original.equals(target)) {
            return true; // Никаких изменений не требуется
        }

        int lenOriginal = original.length();
        int lenTarget = target.length();
        int differences = Math.abs(lenOriginal - lenTarget);

        if (differences > 1) {
            return false; // Разница в длине больше 1 не позволяет выполнить условие одним изменением
        }

        int changes = 0;
        for (int i = 0, j = 0; i < lenOriginal && j < lenTarget; i++, j++) {
            if (original.charAt(i) != target.charAt(j)) {
                changes++;
                if (changes > 1) {
                    return false; // Более одного изменения
                }
                if (lenOriginal > lenTarget) {
                    j--; // Удаление символа из original
                } else if (lenOriginal < lenTarget) {
                    i--; // Удаление символа из target
                }
            }
        }

        return changes<=1;
    }



    public static int strStr(String haystack, String needle) {
        System.out.println(haystack.indexOf(needle));
    return -1;
    }

    public static int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        int interim=0;
        for (int i=0;i<2;i++){
            interim=prices[i]+interim;
        }
        if (interim<=money){
            return money-interim;
        } else return money;

    }

    public static char firstUniqueChar(String s) {
        LinkedHashMap<Character, Integer> orderedMap = new LinkedHashMap<>();
        char []chars = s.toCharArray();
        for (char ch : chars){
            orderedMap.merge(ch, 1, Integer::sum);
        }

        System.out.println(orderedMap);
        for (Map.Entry<Character, Integer> entry: orderedMap.entrySet()){
            if (entry.getValue().equals(1)) {
                return entry.getKey();
            }
        }

        return orderedMap.entrySet().stream()
                .filter(e->e.getValue()==1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse('_');
    }

   public static  boolean isValid(String s){
       Stack<Character> stack = new Stack<>();
       Map<Character, Character> map = new HashMap<>();
       map.put(')', '(');
       map.put('}', '{');
       map.put(']', '[');
       char []chars = s.toCharArray();
       for (char ch:chars){
           if (map.containsValue(ch)){
               stack.push(ch);
           } else {
               if (map.containsKey(ch))
               {
                   if (stack.empty() || map.get(ch)!=stack.pop()){
                       return false;
                   }
               }

           }
       }
       return stack.isEmpty();

   }



    public static int lengthOfLongestSubstring(String s) {
        if ( (s==null) || s.isEmpty() ) {return 0;}
        Map <Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        for (int start=0, end=0; end<s.length();end++){
            char ch = s.charAt(end);
            if (map.containsKey(ch)){
                start = Math.max(start, map.get(ch) + 1);
            }
            map.put(ch,end);
            maxLen = Math.max(maxLen,end-start+1);

        }
        return maxLen;
    }


    public static void main(String[] args) {

//Input: "abcabcbb" → 3   ("abc")
//Input: "bbbbb"    → 1   ("b")
//Input: "pwwkew"   → 3   ("wke")
//Input: ""         → 0

        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring(""));


//        Input: "()[]{}"   → true
//        Input: "(]"       → false
//        Input: "([{}])"   → true
//        Input: "((("      → false
//        System.out.println("()[]{}=>"+isValid("()[]{}"));
//        System.out.println("(]"+isValid("(]"));
//        System.out.println("([{}])"+isValid("([{}])"));
//        System.out.println("((("+isValid("((("));




    }

}
