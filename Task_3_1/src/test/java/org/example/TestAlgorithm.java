package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

public class TestAlgorithm {
    @Test
    void testMoreEqualsPattern(){
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {0, 2, 4, 6, 8, 10, 12};
        int i = 0;
        for (int num:a.find(filePath, "aba")){
            assertEquals(expected[i++],num);
        }
    }

    @Test
    void testEmptyFile(){
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test1";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find(filePath, "aba");
        assertTrue(test.isEmpty());
    }

    @Test
    void testNothingPattern(){
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test2";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        Set<Integer> test = a.find(filePath, "aba");
        assertTrue(test.isEmpty());
    }

    @Test
    void testCommonFile(){
        String filePath = "/home/rend/java/OOP/Task_3_1/src/test/java/org/example/test3";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        int[] expected = {3, 8, 12};
        int i = 0;
        for (int num:a.find(filePath, "cat")){
            assertEquals(expected[i++],num);
        }
    }
}
