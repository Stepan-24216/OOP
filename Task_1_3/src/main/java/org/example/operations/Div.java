package org.example.operations;

import org.example.objects.Expression;
import org.example.objects.Number;

/**
 * Реализация методов для операции деления.
 */
public class Div extends Expression {
    Expression element1;
    Expression element2;

    public Div(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        return "(" + element1 + " / " + element2 + ")";
    }

    /**
     * Взятие производной.
     */
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(element1.derivative(variable), element2),
            new Mul(element1, element2.derivative(variable))), new Mul(element2, element2));
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        if ((element1 instanceof org.example.objects.Number
            && element2 instanceof org.example.objects.Number)) {
            return new org.example.objects.Number(((org.example.objects.Number) element1).getValue() / ((org.example.objects.Number) element2).getValue());
        } else if (element2 instanceof org.example.objects.Number && ((org.example.objects.Number) element2).getValue() == 1) {
            return element1;
        } else if ((element1 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue() == 0) {
            return new Number(0);
        }
        return new Div(element1.simplification(), element2.simplification());
    }

    /**
     * Означивание переменных.
     */
    public int eval(String s) {
        return element1.eval(s) / element2.eval(s);
    }
}
