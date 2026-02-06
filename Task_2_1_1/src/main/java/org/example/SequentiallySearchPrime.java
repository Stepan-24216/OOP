package org.example;

import static org.example.Tools.isPrime;

import java.util.ArrayList;

public class SequentiallySearchPrime {
    public static boolean checkPrimeArray(ArrayList<Integer> numbers) {
        boolean flagNotPrimeNumber = false;
        for (int number : numbers) {
            if (!isPrime(number)) {
                flagNotPrimeNumber = true;
                break;
            }
        }
        return flagNotPrimeNumber;
    }
}
