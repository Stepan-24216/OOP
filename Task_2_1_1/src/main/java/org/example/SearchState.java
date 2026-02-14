package org.example;

/**
 * Класс состояние для всех потоков.
 */
public class SearchState {
    private volatile boolean compositeFound = false;

    public boolean isCompositeFound() {
        return compositeFound;
    }

    public void setCompositeFound() {
        this.compositeFound = true;
    }
}