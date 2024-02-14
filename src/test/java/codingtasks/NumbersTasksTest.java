package codingtasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static codingtasks.NumbersTasks.findMedian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumbersTasksTest {

    @Test
    public void test_findMedian(){
        assertEquals(2,findMedian(1, 3, 2)); // Выведет 2
        assertEquals(8,findMedian(9, 7, 8)); // Выведет 8
        assertEquals(-3,findMedian(-5, -1, -3)); // Выведет -3
    }
    @Test
    public void test_findIntersection(){
        assertArrayEquals(new Integer[] {1, 2, 2, 3},
                NumbersTasks.findIntersection(new int[] {1, 2, 3, 2, 0}, new int[] {5, 1, 2, 7, 3, 2}).toArray());
        assertArrayEquals(new Integer[] {},
                NumbersTasks.findIntersection(new int[] {4, 3, 5}, new int[] {9, 8, 7}).toArray());
        assertArrayEquals(new Integer[] {4,9},
                NumbersTasks.findIntersection(new int[] {4, 9, 5}, new int[] {9, 4, 9, 8, 4}).toArray());

    }
    @Test
    public void testConvertListToRangeString() {
        assertEquals("0-5,8-9,11", NumbersTasks.convertListToRangeString(Arrays.asList(1, 4, 5, 2, 3, 9, 8, 11, 0)));
        assertEquals("1-4", NumbersTasks.convertListToRangeString(Arrays.asList(1, 4, 3, 2)));
        assertEquals("1,4", NumbersTasks.convertListToRangeString(Arrays.asList(1, 4)));
        assertEquals("", NumbersTasks.convertListToRangeString(Arrays.asList())); // Пустой список
        assertEquals("1", NumbersTasks.convertListToRangeString(Arrays.asList(1))); // Один элемент

    }

    @Test
    public void testFindMaxConsecutiveOnes() {
        assertEquals(3, NumbersTasks.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1}));
        assertEquals(2, NumbersTasks.findMaxConsecutiveOnes(new int[]{1, 1, 0}));
        assertEquals(5, NumbersTasks.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1, 1, 1}));
        assertEquals(0, NumbersTasks.findMaxConsecutiveOnes(new int[]{0, 0, 0}));
        assertEquals(1, NumbersTasks.findMaxConsecutiveOnes(new int[]{1}));
        assertEquals(4, NumbersTasks.findMaxConsecutiveOnes(new int[]{1, 0, 1,1, 1, 0, 1, 0, 1}));
    }
    @Test
    public void findMaxGuestsTest() {
        assertEquals(3, NumbersTasks.findMaxGuests(List.of(new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 4}, new int[]{2, 3})));
        assertEquals(1, NumbersTasks.findMaxGuests(List.of(new int[]{1, 2})));
        assertEquals(2, NumbersTasks.findMaxGuests(List.of(new int[]{1, 3}, new int[]{2, 4})));
        assertEquals(0, NumbersTasks.findMaxGuests(List.of()));
    }

    @Test
    public void testMergeIntervals(){
        // Тест 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] expected1 = {{1, 6}, {8, 10}, {15, 18}};
        assertEquals(NumbersTasks.mergeIntervals(intervals1),expected1);
        // Тест 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        int[][] expected2 = {{1, 5}};
        assertEquals(NumbersTasks.mergeIntervals(intervals2),expected2);
        // Тест 3
        int[][] intervals3 = {{1, 3}, {100, 200}, {2, 4}};
        int[][] expected3 = {{1, 4}, {100, 200}};
        assertEquals(NumbersTasks.mergeIntervals(intervals3),expected3);
    }

}
