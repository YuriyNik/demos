package codingJan.sorting;

import java.util.*;

public class Sorting {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public String firstNonRepeating(String s) {
        Map<Character, Integer> count = new HashMap<>();
        Queue<Character> q = new LinkedList<>();

        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
            q.offer(c);

            while (!q.isEmpty() && count.get(q.peek()) > 1) {
                q.poll();
            }

            if (q.isEmpty()) {
                result.append('#');
            } else {
                result.append(q.peek());
            }
        }

        return result.toString();
    }
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] original = {5, 2, 9, 1, 5, 6};
        System.out.print("Original: ");
        printArray(original);

        int[] a1 = Arrays.copyOf(original, original.length);
        bubbleSort(a1);
        System.out.print("Bubble sorted: ");
        printArray(a1);

        int[] a2 = Arrays.copyOf(original, original.length);
        quickSort(a2);
        System.out.print("Quick sorted: ");
        printArray(a2);
    }

}
