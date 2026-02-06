package org.example;

/**
 * Один рабочий поток.
 */
public class PrimeWorker extends Thread {
    static volatile boolean notPrimeNumber = false;
    private final int startIndex;
    private final int endIndex;
    private final java.util.ArrayList<Integer> numbers;

    public PrimeWorker(java.util.ArrayList<Integer> numbers, int startIndex, int endIndex) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public boolean getNotPrimeNumber() {
        return notPrimeNumber;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (!Tools.isPrime(numbers.get(i))) {
                notPrimeNumber = true;
                break;
            }
            if (notPrimeNumber) {
                break;
            }
        }
    }
}
