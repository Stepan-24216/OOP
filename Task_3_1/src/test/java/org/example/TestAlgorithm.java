package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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
    void testMoreEqualsPattern() throws IOException {
        String filePath = "src/test/java/org/example/test";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {0, 2, 4, 6, 8, 10, 12};
        int i = 0;
        for (int num : a.find("aba",filePath)) {
            assertEquals(expected[i++], num);
        }
    }

    /**
     * Пустой файл.
     */
    @Test
    void testEmptyFile() throws IOException{
        String filePath = "src/test/java/org/example/test1";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find("aba",filePath);
        assertTrue(test.isEmpty());
    }

    /**
     * Отсутствие шаблонов в тексте.
     */
    @Test
    void testNothingPattern() throws IOException{
        String filePath = "src/test/java/org/example/test2";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find("aba",filePath);
        assertTrue(test.isEmpty());
    }

    /**
     * Обычный текст.
     */
    @Test
    void testCommonFile() throws IOException{
        String filePath = "src/test/java/org/example/test3";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {3, 8, 12, 18, 23, 27, 33, 38, 42, 48, 53, 57, 63, 68, 72, 78, 83, 87, 93, 98, 102, 108, 113, 117};
        int i = 0;
        for (int num : a.find("cat",filePath)) {
            assertEquals(expected[i++], num);
        }
    }

    /**
     * Обычный текст.
     */
    @Test
    void testIntersectingChunk() throws IOException{
        String filePath = "src/test/java/org/example/test4";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {10};
        int i = 0;
        for (int num : a.find("aaaa",filePath)) {
            assertEquals(expected[i++], num);
        }
    }
}
