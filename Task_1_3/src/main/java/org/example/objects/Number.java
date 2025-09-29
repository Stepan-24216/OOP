package org.example.objects;

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

    /**
     * Взятие производной.
     */
    public Expression derivative(String variable) {
        return new Number(0); // производная константы это нуль
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        return new Number(value);
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        if (value >= 0) {
            return String.valueOf(value);
        } else {
            return ("(" + value + ")");
        }
    }

    /**
     * Означивание переменных.
     */
    public int eval(String s) {
        return value;
    }
}
