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
    return null;
    }

    public static int findMaxConsecutiveOnes(int[] nums) {
        return -1;
    }

    public static int findMaxGuests(List<int[]> stays) {
        return -1;
    }

    }
