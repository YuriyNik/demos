package codingtasks;

import java.util.ArrayList;
import java.util.List;

public class SubsetsSolver {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int idx, int[] nums, List<Integer> path, List<List<Integer>> res) {
        // Базовый случай: все элементы рассмотрены
        if (idx == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        // --- Ветвь 1: Пропустить nums[idx] (НЕТ выбора) ---
        backtrack(idx + 1, nums, path, res);

        // --- Ветвь 2: Взять nums[idx] (ДА выбор) ---

        // 1. Сделать выбор
        path.add(nums[idx]);

        // 2. Перейти к следующему элементу
        backtrack(idx + 1, nums, path, res);

        // 3. Откат (Удалить сделанный выбор для возврата в предыдущее состояние)
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        SubsetsSolver solver = new SubsetsSolver();

        // Тест 1
        int[] nums1 = {1, 2, 3};
        List<List<Integer>> result1 = solver.subsets(nums1);
        System.out.println("--- Тест 1: nums = {1, 2, 3} ---");
        System.out.println("Всего подмножеств: " + result1.size()); // Ожидается 2^3 = 8
        System.out.println("Результат: " + result1);
        /* * Ожидаемый результат (порядок может отличаться):
         * [[], [3], [2], [2, 3], [1], [1, 3], [1, 2], [1, 2, 3]]
         */

        System.out.println("\n" + "--- Тест 2: nums = {0} ---");
        int[] nums2 = {0};
        List<List<Integer>> result2 = solver.subsets(nums2);
        System.out.println("Всего подмножеств: " + result2.size()); // Ожидается 2^1 = 2
        System.out.println("Результат: " + result2);
        // Ожидаемый результат: [[], [0]]



    }
}