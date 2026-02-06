package org.example;

import static org.example.Tools.readFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
        primeNumbers.add(2);
        primeNumbers.add(3);
        primeNumbers.add(5);
        primeNumbers.add(7);
        assertFalse(SequentiallySearchPrime.checkPrimeArray(primeNumbers));

        ArrayList<Integer> noHavePrimeNum = new ArrayList<>();
        noHavePrimeNum.add(2);
        noHavePrimeNum.add(4);
        noHavePrimeNum.add(5);
        assertTrue(SequentiallySearchPrime.checkPrimeArray(noHavePrimeNum));

        ArrayList<Integer> emptyArray = new ArrayList<>();
        assertFalse(SequentiallySearchPrime.checkPrimeArray(emptyArray));

        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test.txt");

        assertTrue(SequentiallySearchPrime.checkPrimeArray(numbers));
    }

    /**
     * Тест на больших данных.
     */
    @Test
    public void testCheckPrimeBigArray() {
        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test3.txt");

        assertTrue(SequentiallySearchPrime.checkPrimeArray(numbers));

        numbers = readFile("src/test/java/org/example/test2.txt");

        assertFalse(SequentiallySearchPrime.checkPrimeArray(numbers));
    }

}
