package codingJan.strings;

import java.util.HashMap;

public class Solution {

    public static String turtlePosition(int size, String moves) {
        int x = 1, y = 1;        // стартовая позиция
        String[] dirs = {"N","E","S","W"};
        int dirIndex = 0;        // N=0, E=1, S=2, W=3

        for(char move : moves.toCharArray()){
            switch(move){
                case 'F':
                    switch(dirs[dirIndex]){
                        case "N": if(y < size) y++; break;
                        case "S": if(y > 1) y--; break;
                        case "E": if(x < size) x++; break;
                        case "W": if(x > 1) x--; break;
                    }
                    break;
                case 'L':
                    dirIndex = (dirIndex + 3) % 4; // поворот влево
                    break;
                case 'R':
                    dirIndex = (dirIndex + 1) % 4; // поворот вправо
                    break;
            }
        }

        return "(" + x + "," + y + "," + dirs[dirIndex] + ")";
    }

    public static String moveTurtle(String input, int size) {
        int x = 1;
        int y = 1;
        char direction = 'N';

        for (char curr : input.toCharArray()) {

            // движение вперед
            if (curr == 'F') {
                if (direction == 'N' && y < size) y++;
                else if (direction == 'S' && y > 1) y--;
                else if (direction == 'E' && x < size) x++;
                else if (direction == 'W' && x > 1) x--;
            }

            // поворот направо
            else if (curr == 'R') {
                if (direction == 'N') direction = 'E';
                else if (direction == 'E') direction = 'S';
                else if (direction == 'S') direction = 'W';
                else if (direction == 'W') direction = 'N';
            }

            // поворот налево
            else if (curr == 'L') {
                if (direction == 'N') direction = 'W';
                else if (direction == 'W') direction = 'S';
                else if (direction == 'S') direction = 'E';
                else if (direction == 'E') direction = 'N';
            }
        }

        return "(" + x + "," + y + "," + direction + ")";
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            // If character exists in map, jump left pointer to the right of the previous occurrence
            if (map.containsKey(current)) {
                left = Math.max(left, map.get(current) + 1);
            }

            map.put(current, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.lengthOfLongestSubstring("abcabcbb")); // Output: 3 ("abc")
        System.out.println(sol.lengthOfLongestSubstring("bbbbb"));    // Output: 1 ("b")
        System.out.println(sol.lengthOfLongestSubstring("pwwkew"));   // Output: 3 ("wke")
    }
}
