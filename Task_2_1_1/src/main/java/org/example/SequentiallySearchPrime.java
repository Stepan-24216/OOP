package org.example;

import java.util.ArrayList;

/**
 * Поиск с помощью последовательной проверки.
 */
public class SequentiallySearchPrime implements NoPrimeSearches{
    public boolean hasCompositeNumber(ArrayList<Integer> numbers) {
        boolean flagNotPrimeNumber = false;
        for (int number : numbers) {
            if (!NoPrimeSearches.isPrime(number)) {
                flagNotPrimeNumber = true;
                break;
            }
        }
        return flagNotPrimeNumber;
    }
}
