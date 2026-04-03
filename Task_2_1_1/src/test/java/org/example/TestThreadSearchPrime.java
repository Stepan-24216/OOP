package org.example;

import static org.example.ToolsReadFiles.readFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.example.checkers.ThreadSearchPrime;
import org.junit.jupiter.api.Test;

/**
 * Поиск не простых чисел с помощью кастомного количества потоков.
 */
public class TestThreadSearchPrime {
    /**
     * Тест на маленьких данных.
     */
    @Test
    public void testCheckPrimeSmallArray() {
        ThreadSearchPrime threadSearchPrime = new ThreadSearchPrime(8);
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        primeNumbers.add(2);
        primeNumbers.add(3);
        primeNumbers.add(5);
        primeNumbers.add(7);
        assertFalse(threadSearchPrime.hasCompositeNumber(primeNumbers));

        ArrayList<Integer> noHavePrimeNum = new ArrayList<>();
        noHavePrimeNum.add(2);
        noHavePrimeNum.add(4);
        noHavePrimeNum.add(5);
        assertTrue(threadSearchPrime.hasCompositeNumber(noHavePrimeNum));

        ArrayList<Integer> emptyArray = new ArrayList<>();
        assertFalse(threadSearchPrime.hasCompositeNumber(emptyArray));

        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test.txt");

        assertTrue(threadSearchPrime.hasCompositeNumber( numbers));
    }

    /**
     * Тест на больших данных.
     */
    @Test
    public void testCheckPrimeBigArray() {
        ThreadSearchPrime threadSearchPrime = new ThreadSearchPrime(8);
        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test3.txt");

        assertTrue(threadSearchPrime.hasCompositeNumber(numbers));

        numbers = readFile("src/test/java/org/example/test2.txt");

        assertFalse(threadSearchPrime.hasCompositeNumber(numbers));
    }
}
