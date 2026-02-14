package org.example;

import static org.example.ToolsReadFiles.readFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.example.checkers.SequentiallySearchPrime;
import org.junit.jupiter.api.Test;

/**
 * Поиск не простых чисел с помощью последовательной проверки.
 */
public class TestSequentiallySearchPrime {
    /**
     * Тест на маленьких данных.
     */
    @Test
    public void testCheckPrimeSmallArray() {
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        SequentiallySearchPrime sequentiallySearchPrime = new SequentiallySearchPrime();
        primeNumbers.add(2);
        primeNumbers.add(3);
        primeNumbers.add(5);
        primeNumbers.add(7);
        assertFalse(sequentiallySearchPrime.hasCompositeNumber(primeNumbers));

        ArrayList<Integer> noHavePrimeNum = new ArrayList<>();
        noHavePrimeNum.add(2);
        noHavePrimeNum.add(3);
        noHavePrimeNum.add(4);
        noHavePrimeNum.add(5);
        assertTrue(sequentiallySearchPrime.hasCompositeNumber(noHavePrimeNum));

        ArrayList<Integer> emptyArray = new ArrayList<>();
        assertFalse(sequentiallySearchPrime.hasCompositeNumber(emptyArray));

        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test.txt");

        assertTrue(sequentiallySearchPrime.hasCompositeNumber(numbers));
    }

    /**
     * Тест на больших данных.
     */
    @Test
    public void testCheckPrimeBigArray() {
        ArrayList<Integer> numbers;
        SequentiallySearchPrime sequentiallySearchPrime = new SequentiallySearchPrime();

        numbers = readFile("src/test/java/org/example/test3.txt");

        assertTrue(sequentiallySearchPrime.hasCompositeNumber(numbers));

        numbers = readFile("src/test/java/org/example/test2.txt");

        assertFalse(sequentiallySearchPrime.hasCompositeNumber(numbers));
    }

}
