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
//1481. Least Number of Unique Integers after K Removals
//https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals
    public static int findLeastNumOfUniqueInts(int[] arr, int k) {
        if (arr==null) return -1;
        if (arr.length<1) return -1;
        Map<Integer,Integer> mapElements = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            mapElements.put(arr[i],mapElements.getOrDefault(arr[i],0)+1);
        }
     //   System.out.println(mapElements);

        // Создаем список из элементов map и сортируем его по значениям
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(mapElements.entrySet());
        entries.sort(Map.Entry.comparingByValue());

        for (Map.Entry<Integer, Integer> entry : entries) {
            if (k==0) {
                break; // Прекращаем удаление, если достигли или превысили заданное число
            }
            int value = entry.getValue();
            if (k - value >= 0) {
                k -= value;
                mapElements.remove(entry.getKey());
            }
        }

        return mapElements.size();
    }

    public static int findJudge(int N, int[][] trust) {
        int[] in = new int[N + 1];
        int[] out = new int[N + 1];
        for (int[] a : trust) {
            out[a[0]]++;
            in[a[1]]++;
            System.out.print(Arrays.toString(in));
            System.out.print(Arrays.toString(out));
            System.out.println();
        }
        for (int i = 1; i <= N; i++) {
            if (in[i] == N - 1 && out[i] == 0)
                return i;
        }


        return -1;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] fl : flights) {
            int currSrc = fl[0], neigh = fl[1], neighDistFromSrc = fl[2];
            graph.computeIfAbsent(currSrc, key -> new ArrayList<>()).add(new int[]{neigh, neighDistFromSrc});
        }

        int[] distFromSource = new int[n];
        Arrays.fill(distFromSource, Integer.MAX_VALUE);
        Queue<int[]> que = new LinkedList<>();
        int minLen = Integer.MAX_VALUE, stops = 0;

        que.offer(new int[]{src, 0});
        while (!que.isEmpty() && stops <= k) {
            int size = que.size();
            while (size-- > 0) {
                int[] currPr = que.poll();
                for (int[] neigh : graph.getOrDefault(currPr[0], new ArrayList<>())) {
                    if (distFromSource[neigh[0]] > currPr[1] + neigh[1]) {
                        distFromSource[neigh[0]] = currPr[1] + neigh[1];
                        if (neigh[0] == dst)
                            minLen = Math.min(minLen, distFromSource[neigh[0]]);
                        que.offer(new int[]{neigh[0], distFromSource[neigh[0]]});
                    }
                }
            }
            stops++;
        }
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    public static int[] sortedSquares(int[] nums) {
        int[] output = new int[nums.length];
        for (int i=0;i<nums.length;i++)
        {
            output[i]= nums[i]*nums[i];
            //System.out.println(Arrays.toString(output));
        }
        Arrays.sort(output);
        return output;
    }
//2540. Minimum Common Value
    public int getCommon(int[] nums1, int[] nums2) {
        Map<Integer,Integer> mnum = new HashMap<>();
        for (int j : nums2) {
            mnum.put(j, mnum.getOrDefault(j, 0)+1);
        }
        for (int v : nums1) {
            if (mnum.getOrDefault(v,0)>0) return v;
        }
        return  -1;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> setNums1 = new HashSet<>();
        for (Integer num:nums1){
            setNums1.add(num);
        }
        Set<Integer> result = new HashSet<>();
        for (Integer num:nums2){
            if (setNums1.contains(num)) result.add(num);
        }
    return result.stream().mapToInt(Integer::intValue).toArray();
    }
    public static void main(String[] args) {
        int[] nums= {-4,-1,0,3,10};
        System.out.println("sortedSquares="+Arrays.toString(sortedSquares(nums)));

    }
    }
