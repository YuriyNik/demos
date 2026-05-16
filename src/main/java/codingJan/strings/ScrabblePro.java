package codingJan.strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrabblePro {

    // Справочник стоимости букв
    private static final Map<Character, Integer> POINTS = new HashMap<>() {{
        put('a', 1); put('e', 1); put('i', 1); put('o', 1);
        put('d', 2); put('g', 2);
        put('b', 3); put('c', 3); put('m', 3); put('p', 3);
        put('q', 10); put('z', 10);
        // Остальные буквы можно добавить аналогично
    }};

    public static void solveScrabble(String hand, String word) {
        Map<Character, Integer> handMap = new HashMap<>();
        for (char c : hand.toCharArray()) {
            handMap.put(c, handMap.getOrDefault(c, 0) + 1);
        }

        int totalScore = 0;
        boolean canMake = true;
        List<String> details = new ArrayList<>();

        for (char c : word.toCharArray()) {
            // 1. Проверяем, есть ли буква
            if (handMap.getOrDefault(c, 0) > 0) {
                handMap.put(c, handMap.get(c) - 1);
                int p = POINTS.getOrDefault(c, 1);
                totalScore += p;
                details.add(c + " (+" + p + ")");
            }
            // 2. Если буквы нет, ищем джокер '*'
            else if (handMap.getOrDefault('*', 0) > 0) {
                handMap.put('*', handMap.get('*') - 1);
                details.add(c + " (джокер, 0)");
                // Джокер обычно дает 0 очков
            }
            // 3. Совсем ничего нет
            else {
                canMake = false;
                break;
            }
        }

        if (canMake) {
            System.out.println("Слово '" + word + "' собрано!");
            System.out.println("Очки: " + totalScore + " " + details);
        } else {
            System.out.println("Невозможно собрать '" + word + "'");
        }
    }

    public static void main(String[] args) {
        // Пример 1: Есть все буквы
        solveScrabble("apple*", "apple");

        // Пример 2: Используем джокер вместо буквы 'z'
        solveScrabble("piza*", "pizza");

        // Пример 3: Не хватает букв даже с джокером
        solveScrabble("abc*", "queen");
    }

}
