package org.example;

import java.util.ArrayList;

public interface NoPrimeSearches {
    /**
     * Проверка простоты.
     */
    static boolean isPrime(int number) {
        if (number <= 1 || (number % 2 == 0 && number != 2)) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number) + 1; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    boolean hasCompositeNumber(ArrayList<Integer> numbers);
}
