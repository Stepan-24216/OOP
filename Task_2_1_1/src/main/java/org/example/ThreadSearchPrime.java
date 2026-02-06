package org.example;

import java.util.ArrayList;

/**
 * Поиск не простых чисел с помощью кастомного количества потоков.
 */
public class ThreadSearchPrime {
    public static boolean checkPrimeArray(int threadsCount, ArrayList<Integer> numbers) {
        PrimeWorker.notPrimeNumber = false;
        PrimeWorker[] threads = new PrimeWorker[threadsCount];
        int numbersPerThread = numbers.size() / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int startIndex = i * numbersPerThread;
            int endIndex = (i == threadsCount - 1) ? numbers.size() : startIndex + numbersPerThread;
            threads[i] = new PrimeWorker(numbers, startIndex, endIndex);
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < threadsCount; i++) {
            if (threads[i].getNotPrimeNumber()) {
                return true;
            }
        }
        return false;
    }
}
