package org.example.objects;

/**
 * Абстрактный класс с объявлением операций работы с методами.
 */
public abstract class Expression {
    /**
     * Взятие производной.
     */
    public abstract Expression derivative(String variable);

    /**
     * Означивание переменных.
     */
    public abstract int eval(String s);

    /**
     * Преобразование в строку.
     */
    public abstract String toString();

    /**
     * Упрощение выражения.
     */
    public abstract Expression simplification();

    public void print() {
        System.out.println(this);
    }
}

