package org.example.checkers;

import java.util.ArrayList;
import org.example.NoPrimeSearches;

/**
 * Поиск с помощью последовательной проверки.
 */
public class SequentiallySearchPrime implements NoPrimeSearches {
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
