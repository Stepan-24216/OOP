package com.example.heapsort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {


    @Test
    void testSmallArray() {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testFiveElementArray() {
        int[] arr = {5, 2, 8, 1, 9};
        int[] expected = {1, 2, 5, 8, 9};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {1};
        int[] expected = {1};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testDuplicateElements() {
        int[] arr = {3, 3, 3, 3};
        int[] expected = {3, 3, 3, 3};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testReverseSortedArray() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testNullArray() {
        int[] arr = null;

        assertDoesNotThrow(() -> HeapSort.sort(arr));
        assertNull(arr);
    }

    @Test
    void testIsSortedMethod() {
        assertTrue(HeapSort.isSorted(new int[]{1, 2, 3}));
        assertTrue(HeapSort.isSorted(new int[]{1}));
        assertTrue(HeapSort.isSorted(new int[]{}));
        assertTrue(HeapSort.isSorted(null));
        assertFalse(HeapSort.isSorted(new int[]{3, 2, 1}));
        assertFalse(HeapSort.isSorted(new int[]{1, 3, 2}));
    }

}