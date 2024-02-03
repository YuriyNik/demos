package codingtasks;

import org.junit.jupiter.api.Test;

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
                NumbersTasks.findIntersection(new int[] {1, 2, 3, 2, 0}, new int[] {5, 1, 2, 7, 3, 2}).toArray(new Integer[0]));
        assertArrayEquals(new Integer[] {},
                NumbersTasks.findIntersection(new int[] {4, 9, 5}, new int[] {9, 8, 7}).toArray(new Integer[0]));
        assertArrayEquals(new Integer[] {9},
                NumbersTasks.findIntersection(new int[] {4, 9, 5}, new int[] {9, 4, 9, 8, 4}).toArray(new Integer[0]));

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
    }
    @Test
    void findMaxGuestsTest() {
        assertEquals(3, NumbersTasks.findMaxGuests(List.of(new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 4}, new int[]{2, 3})));
        assertEquals(1, NumbersTasks.findMaxGuests(List.of(new int[]{1, 2})));
        assertEquals(2, NumbersTasks.findMaxGuests(List.of(new int[]{1, 3}, new int[]{2, 4})));
        assertEquals(0, NumbersTasks.findMaxGuests(List.of()));
    }

}