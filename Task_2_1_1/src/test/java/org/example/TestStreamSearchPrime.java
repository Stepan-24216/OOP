package org.example;

import static org.example.ToolsReadFiles.readFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Поиск не простых чисел с помощью метода Stream.
 */
public class TestStreamSearchPrime {
    /**
     * Тест на маленьких данных.
     */
    @Test
    public void testCheckPrimeSmallArray() {
        StreamSearchPrime streamSearchPrime = new StreamSearchPrime();
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        primeNumbers.add(2);
        primeNumbers.add(3);
        primeNumbers.add(5);
        primeNumbers.add(7);
        assertFalse(streamSearchPrime.hasCompositeNumber(primeNumbers));

        ArrayList<Integer> noHavePrimeNum = new ArrayList<>();
        noHavePrimeNum.add(2);
        noHavePrimeNum.add(4);
        noHavePrimeNum.add(5);
        assertTrue(streamSearchPrime.hasCompositeNumber(noHavePrimeNum));

        ArrayList<Integer> emptyArray = new ArrayList<>();
        assertFalse(streamSearchPrime.hasCompositeNumber(emptyArray));

        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test.txt");

        assertTrue(streamSearchPrime.hasCompositeNumber(numbers));
    }

    /**
     * Тест на больших данных.
     */
    @Test
    public void testCheckPrimeBigArray() {
        StreamSearchPrime streamSearchPrime = new StreamSearchPrime();
        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test3.txt");

        assertTrue(streamSearchPrime.hasCompositeNumber(numbers));

        numbers = readFile("src/test/java/org/example/test2.txt");

        assertFalse(streamSearchPrime.hasCompositeNumber(numbers));
    }
}
