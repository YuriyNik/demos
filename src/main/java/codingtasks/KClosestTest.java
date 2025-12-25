package codingtasks;

import java.util.Arrays;
import java.util.PriorityQueue;

class KClosest {

    public int[][] kClosest(int[][] points, int k) {
        // ... (Ваша реализация)
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(dist(b), dist(a)) // Max-Heap по расстоянию
        );

        for (int[] p : points) {
            pq.offer(p);
            if (pq.size() > k) pq.poll();
        }

        int[][] res = new int[k][];
        // Извлекаем элементы. Порядок не важен, но массив должен быть заполнен.
        for (int i = 0; i < k; i++) res[i] = pq.poll();
        return res;
    }

    private int dist(int[] p) {
        // Квадрат расстояния
        return p[0]*p[0] + p[1]*p[1];
    }
}

public class KClosestTest {

    // Вспомогательный метод для удобного сравнения результатов
    private static boolean compareResults(int[][] actual, int[][] expected) {
        if (actual.length != expected.length) return false;

        // Для сравнения, преобразуем массивы в списки и сортируем их.
        // Это необходимо, так как порядок элементов в итоговом массиве не важен.
        var actualList = Arrays.stream(actual)
                .map(p -> p[0] + "," + p[1])
                .sorted()
                .toList();

        var expectedList = Arrays.stream(expected)
                .map(p -> p[0] + "," + p[1])
                .sorted()
                .toList();

        return actualList.equals(expectedList);
    }

    public static void main(String[] args) {
        KClosest solver = new KClosest();

        // Тест 1: Базовый случай
        int[][] points1 = {{1, 3}, {-2, 2}};
        int k1 = 1;
        // Расстояния: (1,3) -> 10, (-2,2) -> 8. Ближайшая: (-2,2)
        int[][] expected1 = {{-2, 2}};
        int[][] result1 = solver.kClosest(points1, k1);

        System.out.println("--- Тест 1: Базовый случай (K=1) ---");
        System.out.println("Вход: " + Arrays.deepToString(points1) + ", K=" + k1);
        System.out.println("Ожидаемый результат: " + Arrays.deepToString(expected1));
        System.out.println("Полученный результат: " + Arrays.deepToString(result1));
        System.out.println("Результат теста: " + (compareResults(result1, expected1) ? "Успех ✅" : "Провал ❌"));
        System.out.println("------------------------------------------");


        // Тест 2: K > 1 и все точки
        int[][] points2 = {{3, 3}, {5, -1}, {-2, 4}};
        int k2 = 2;
        // Расстояния: (3,3) -> 18, (5,-1) -> 26, (-2,4) -> 20.
        // 2 ближайших: (3,3) и (-2,4)
        int[][] expected2 = {{3, 3}, {-2, 4}};
        int[][] result2 = solver.kClosest(points2, k2);

        System.out.println("--- Тест 2: K > 1 и сравнение расстояний ---");
        System.out.println("Вход: " + Arrays.deepToString(points2) + ", K=" + k2);
        System.out.println("Ожидаемый результат: " + Arrays.deepToString(expected2));
        System.out.println("Полученный результат: " + Arrays.deepToString(result2));
        System.out.println("Результат теста: " + (compareResults(result2, expected2) ? "Успех ✅" : "Провал ❌"));
        System.out.println("------------------------------------------");


        // Тест 3: Одинаковые расстояния
        int[][] points3 = {{1, 1}, {-1, -1}, {0, 0}};
        int k3 = 2;
        // Расстояния: (1,1) -> 2, (-1,-1) -> 2, (0,0) -> 0.
        // 2 ближайших: (0,0) и любая из (1,1)/(-1,-1)
        int[][] expected3_option1 = {{0, 0}, {1, 1}};
        int[][] result3 = solver.kClosest(points3, k3);

        System.out.println("--- Тест 3: Одинаковые расстояния ---");
        System.out.println("Вход: " + Arrays.deepToString(points3) + ", K=" + k3);
        System.out.println("Ожидаемый результат: " + Arrays.deepToString(expected3_option1) + " (или другой порядок/выбор)");
        // В этом случае compareResults проверит, что получены правильные значения, независимо от того, какая из точек с одинаковым расстоянием выбрана.
        System.out.println("Полученный результат: " + Arrays.deepToString(result3));

        // Для этого теста сравнение должно быть немного умнее или просто проверить, что (0,0) присутствует и еще одна точка с расстоянием 2.
        // Используем универсальный compareResults:
        int[][] expected3 = {{0, 0}, {-1, -1}}; // Выбираем любой валидный ответ
        System.out.println("Результат теста: " + (compareResults(result3, expected3) || compareResults(result3, expected3_option1) ? "Успех ✅" : "Провал ❌"));
        System.out.println("------------------------------------------");
    }
}
