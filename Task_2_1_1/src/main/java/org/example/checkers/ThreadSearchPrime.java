package org.example.checkers;

import java.util.ArrayList;
import org.example.NoPrimeSearches;
import org.example.PrimeWorker;
import org.example.SearchState;

/**
 * Поиск не простых чисел с помощью кастомного количества потоков.
 */
public class ThreadSearchPrime implements NoPrimeSearches {
    private final int threadsCount;

    public ThreadSearchPrime(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    public boolean hasCompositeNumber(ArrayList<Integer> numbers) {
        SearchState sharedState = new SearchState();
        PrimeWorker[] threads = new PrimeWorker[threadsCount];
        int numbersPerThread = numbers.size() / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int startIndex = i * numbersPerThread;
            int endIndex = (i == threadsCount - 1) ? numbers.size() : startIndex + numbersPerThread;
            threads[i] = new PrimeWorker(numbers, startIndex, endIndex, sharedState);
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Не удалось дождаться завершения потока " + i, e);
            }
        }

        return sharedState.isCompositeFound();
    }
}
