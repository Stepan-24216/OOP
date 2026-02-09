package org.example;

import java.util.ArrayList;

/**
 * Поиск не простых чисел с помощью метода Stream.
 */
public class StreamSearchPrime implements NoPrimeSearches {
    public boolean hasCompositeNumber(ArrayList<Integer> numbers) {
        return numbers.parallelStream()
            .anyMatch(n -> !NoPrimeSearches.isPrime(n));
    }
}
