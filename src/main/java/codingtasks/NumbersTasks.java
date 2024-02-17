package codingtasks;

import java.util.*;

public class NumbersTasks {

    public static int findMedian(int a, int b, int c) {
        int[] array={a,b,c};
        Arrays.sort(array);
        return array[1];
    }

    public static List<Integer> findIntersection(int[] nums1, int[] nums2) {
        Map<Integer,Integer> countMap = new HashMap<>();
        List<Integer> intesection = new ArrayList<>();
        for (int num:nums1){
            countMap.put(num,countMap.getOrDefault(num,0)+1);
        }
        for (int num:nums2){
            if (countMap.getOrDefault(num,0)>0){
                intesection.add(num);
                countMap.put(num,countMap.get(num)-1);
            }

        }
        Collections.sort(intesection);
        return intesection;
    }

    public static String convertListToRangeString(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return "";
        }

        Collections.sort(numbers); // Сортируем список для последовательного прохода

        StringBuilder rangeStr = new StringBuilder();
        Integer start = numbers.get(0);
        Integer end = start;

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) == end + 1) {
                end = numbers.get(i);
            } else {
                appendRange(rangeStr, start, end);
                start = numbers.get(i);
                end = start;
            }
        }
        appendRange(rangeStr, start, end); // Добавляем последний диапазон

        return rangeStr.toString();
    }
    private static void appendRange(StringBuilder rangeStr, Integer start, Integer end) {
        if (rangeStr.length() > 0) {
            rangeStr.append(",");
        }
        if (!start.equals(end)) {
            rangeStr.append(start).append("-").append(end);
        } else {
            rangeStr.append(start);
        }
    }

    public static int findMaxConsecutiveOnes(int[] nums) {
        int max_length=0;
        int curr_length=0;
        int prev_length=0;
        for (int num:nums){
         if (num==1) {
             curr_length++;
         } else {
                 max_length = Math.max((curr_length+prev_length),max_length);
                 prev_length=curr_length;
                 curr_length=0;
         }

        }
        return Math.max((curr_length+prev_length),max_length);
    }

    public static int findMaxGuests(List<int[]> stays) {
        return -1;
    }
    public static int[][] mergeIntervals(int[][] intervals) {

        if (intervals==null) return null;
        if (intervals.length<=1) return intervals;

        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));
        LinkedList<int[]> merged = new LinkedList<>();
        for (int[] interval:intervals){
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
                merged.add(interval);
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums); // Шаг 1: Сортировка массива
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) { // Пропуск дубликатов
                int low = i + 1 ;
                int high = nums.length - 1;
                int sum = 0 - nums[i];

                while (low < high) {
                    if (nums[low] + nums[high] == sum) {
                        result.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        while (low < high && nums[low] == nums[low+1]) low++; // Пропуск дубликатов
                        while (low < high && nums[high] == nums[high-1]) high--; // Пропуск дубликатов
                        low++;
                        high--;
                    } else if (nums[low] + nums[high] < sum) {
                        while (low < high && nums[low] == nums[low+1]) low++; // Пропуск дубликатов
                        low++;
                    } else {
                        while (low < high && nums[high] == nums[high-1]) high--; // Пропуск дубликатов
                        high--;
                    }
                }
            }
        }
        return result;
    }

    public static int[] countBits(int num) {
            int[] f = new int[num + 1];
            for (int i=1; i<=num; i++) f[i] = f[i >> 1] + (i & 1);
            return f;
        }


    public static void main(String[] args) {
        int[] input = {-1,0,1,2,-1,-4};
        System.out.println(threeSum(input));
        System.out.println(Arrays.toString(countBits(1)));
        System.out.println(Arrays.toString(countBits(2)));
        System.out.println(Arrays.toString(countBits(5)));
        System.out.println(Arrays.toString(countBits(20)));

    }
    }
