package org.example;

/**
 * Реализация методов для чисел.
 */
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Expression derivative(String variable) {
        return new Number(0); // производная константы это нуль
    }

    public Expression simplification() {
        return new Number(value);
    }

    public String toString() {
        if (value >= 0) {
            return String.valueOf(value);
        } else {
            return ("(" + value + ")");
        }
    }

    public int eval(String s) {
        return value;
    }
}
