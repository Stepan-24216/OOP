package org.example;

/**
 * Один рабочий поток.
 */
public class PrimeWorker extends Thread {
    private final SearchState sharedState;
    private final int startIndex;
    private final int endIndex;
    private final java.util.ArrayList<Integer> numbers;

    /**
     * Конструктор.
     */
    public PrimeWorker(java.util.ArrayList<Integer> numbers, int startIndex, int endIndex,
                       SearchState sharedState) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sharedState = sharedState;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (sharedState.isCompositeFound()) {
                return;
            }
            if (!NoPrimeSearches.isPrime(numbers.get(i))) {
                sharedState.setCompositeFound();
                return;
            }
        }
    }
}
