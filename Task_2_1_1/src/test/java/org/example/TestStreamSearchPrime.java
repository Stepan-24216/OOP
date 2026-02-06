package org.example;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.example.Tools.readFile;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStreamSearchPrime {
    @Test
    public void testCheckPrimeSmallArray() {
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        primeNumbers.add(2);
        primeNumbers.add(3);
        primeNumbers.add(5);
        primeNumbers.add(7);
        assertFalse(StreamSearchPrime.checkPrimeArray(primeNumbers));

        ArrayList<Integer> noHavePrimeNum = new ArrayList<>();
        noHavePrimeNum.add(2);
        noHavePrimeNum.add(4);
        noHavePrimeNum.add(5);
        assertTrue(StreamSearchPrime.checkPrimeArray(noHavePrimeNum));

        ArrayList<Integer> emptyArray = new ArrayList<>();
        assertFalse(StreamSearchPrime.checkPrimeArray(emptyArray));

        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test.txt");

        assertTrue(StreamSearchPrime.checkPrimeArray(numbers));
    }

    @Test
    public void testCheckPrimeBigArray() {
        ArrayList<Integer> numbers;
        numbers = readFile("src/test/java/org/example/test3.txt");

        assertTrue(StreamSearchPrime.checkPrimeArray(numbers));

        numbers = readFile("src/test/java/org/example/test2.txt");

        assertFalse(StreamSearchPrime.checkPrimeArray(numbers));
    }
}
