package org.example;

/**
 * Абстрактный класс с объявлением операций работы с методами.
 */
public abstract class Expression {
    public abstract Expression derivative(String variable);

    public abstract int eval(String s);

    public abstract String toString();

    public void print() {
        System.out.println(this);
    }
}

