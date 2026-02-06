package org.example;

import java.util.ArrayList;

public class StreamSearchPrime {
    public static boolean checkPrimeArray(ArrayList<Integer> numbers) {
        return numbers.parallelStream()
            .anyMatch(n -> !Tools.isPrime(n));
    }
}
