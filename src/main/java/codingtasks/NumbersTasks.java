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
        return -1;
    }

    public static int findMaxGuests(List<int[]> stays) {
        return -1;
    }

    }
