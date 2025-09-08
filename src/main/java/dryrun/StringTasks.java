package dryrun;

public class StringTasks {
    public String firstEvenLengthWord(String s){
        String[] words = s.split("\\s");
        for (String word:words){
            if (word.length()%2==0)
            {
                return word;
            }
        }
        return "";
    }
    public String longestWord(String s){
        String[] words = s.split("\\s");
        String longest = "";
        for (String word:words){
            if (word.length()>longest.length())
            {
                longest=word;
            }
        }

        return longest;
    }
}
