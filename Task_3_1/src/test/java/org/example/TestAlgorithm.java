package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Тесты для Алгоритма.
 */
public class TestAlgorithm {
    /**
     * Множество искомых подстрок подряд.
     */
    @Test
    void testMoreEqualsPattern() {
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {0, 2, 4, 6, 8, 10, 12};
        int i = 0;
        for (int num : a.find(filePath, "aba")) {
            assertEquals(expected[i++], num);
        }
    }

    /**
     * Пустой файл.
     */
    @Test
    void testEmptyFile() {
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test1";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find(filePath, "aba");
        assertTrue(test.isEmpty());
    }

    /**
     * Отсутствие шаблонов в тексте.
     */
    @Test
    void testNothingPattern() {
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test2";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find(filePath, "aba");
        assertTrue(test.isEmpty());
    }

    /**
     * Обычный текст.
     */
    @Test
    void testCommonFile() {
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test3";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {3, 8, 12, 18, 23, 27, 33, 38, 42, 48, 53, 57, 63, 68, 72, 78, 83, 87, 93, 98, 102, 108, 113, 117};
        int i = 0;
        for (int num : a.find(filePath, "cat")) {
            assertEquals(expected[i++], num);
        }
    }
}
