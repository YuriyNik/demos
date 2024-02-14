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
    }
