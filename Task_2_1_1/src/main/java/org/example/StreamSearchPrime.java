package org.example;

import java.util.ArrayList;

/**
 * Поиск не простых чисел с помощью метода Stream.
 */
public class StreamSearchPrime {
    public static boolean checkPrimeArray(ArrayList<Integer> numbers) {
        return numbers.parallelStream()
            .anyMatch(n -> !Tools.isPrime(n));
    }
}
