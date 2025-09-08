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
        return "";
    }
}
