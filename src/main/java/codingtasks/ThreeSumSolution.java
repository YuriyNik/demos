package codingtasks;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class ThreeSum {

    /**
     * @brief Находит все уникальные тройки (a, b, c) в массиве, где a + b + c = 0.
     * @param nums Массив целых чисел.
     * @return Список уникальных троек.
     * @complexity Time: O(N^2), Space: O(N) или O(log N) (зависит от сортировки).
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (i>0 && nums[i]==nums[i-1]) continue;
            int left=i+1;
            int right = nums.length-1;
            while (left<right){
                int sum=nums[i]+nums[left]+nums[right];
                if ((sum)==0) {
                    result.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    left++;
                    right--;
                    while (left<right && nums[left]==nums[left-1]) left++;
                    while (left<right && nums[right]==nums[right+1]) right--;
                } else {
                    if (sum<0) left++; else right--;
                }
            }
        }


        return result;
    }
}

public class ThreeSumSolution {

    // Вспомогательный метод для удобной печати результатов
    private static void printTestResult(String testName, List<List<Integer>> result, List<List<Integer>> expected) {
        // Упрощенная проверка: в реальной жизни нужно проверять,
        // что списки содержат одинаковые элементы, независимо от порядка
        System.out.println("--- " + testName + " ---");
        System.out.println("Ожидаемый результат: " + expected);
        System.out.println("Фактический результат: " + result);
        // Примечание: для строгой проверки надо сортировать оба списка троек
        if (result.size() == expected.size() && result.containsAll(expected)) {
            System.out.println("Тест пройден ✅");
        } else {
            System.out.println("Тест не пройден ❌ (Проверьте логику!)");
        }
        System.out.println("------------------------------------------");
    }

    // Пустой вызов и тесты для тренировки
    public static void main(String[] args) {
        ThreeSum solver = new ThreeSum();

        // Тест 1: Стандартный случай
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> expected1 = List.of(List.of(-1, -1, 2), List.of(-1, 0, 1));
        List<List<Integer>> result1 = solver.threeSum(nums1);
        printTestResult("Тест 1 (Стандартный)", result1, expected1);

        // Тест 2: Без результата
        int[] nums2 = {0, 1, 1};
        List<List<Integer>> expected2 = List.of();
        List<List<Integer>> result2 = solver.threeSum(nums2);
        printTestResult("Тест 2 (Без результата)", result2, expected2);

        // Тест 3: С нулями
        int[] nums3 = {0, 0, 0};
        List<List<Integer>> expected3 = List.of(List.of(0, 0, 0));
        List<List<Integer>> result3 = solver.threeSum(nums3);
        printTestResult("Тест 3 (Только нули)", result3, expected3);

        // Тест 4: Случай с множеством дубликатов
        int[] nums4 = {-2, 0, 0, 2, 2};
        List<List<Integer>> expected4 = List.of(List.of(-2, 0, 2));
        List<List<Integer>> result4 = solver.threeSum(nums4);
        printTestResult("Тест 4 (Дубликаты)", result4, expected4);
    }
}