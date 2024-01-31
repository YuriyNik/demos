package codingtasks;

import java.util.List;
import java.util.Objects;

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
    public static List<List<String>> getAnagram(String[] strings) {

    return null;
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

}
