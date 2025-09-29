package org.example.operations;

import org.example.objects.Expression;
import org.example.objects.Number;
import org.example.objects.Variable;

/**
 * Реализация методов для операции умножения.
 */
public class Mul extends Expression {
    Expression element1;
    Expression element2;

    public Mul(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        return "(" + element1 + " * " + element2 + ")";
    }

    /**
     * Взятие производной.
     */
    public Expression derivative(String variable) {
        if (!(element1 instanceof org.example.objects.Number) && element2 instanceof org.example.objects.Number) {
            return new Mul(element1.derivative(variable), element2);
        } else if (element1 instanceof org.example.objects.Number && !(element2 instanceof org.example.objects.Number)) {
            return new Mul(element1, element2.derivative(variable));
        }
        Expression mul1 = element1.derivative(variable);
        Expression mul2 = element2.derivative(variable);
        Expression summand1 = new Mul(element1.derivative(variable), element2);
        Expression summand2 = new Mul(element1, element2.derivative(variable));
        // if (mul1 instanceof Number && ((Number) mul1).getValue() == 1
        //     && element2 instanceof Variable) {
        //     summand1 = element2;
        // }
        // if (mul2 instanceof Number
        //     && ((Number) mul2).getValue() == 1 && element1 instanceof Variable) {
        //     summand2 = element1;
        // }
        return new Add(summand1, summand2);
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        if ((element1 instanceof org.example.objects.Number && ((org.example.objects.Number) element1).getValue() == 1
            && element2 instanceof Variable)) {
            return element2;
        } else if (element2 instanceof org.example.objects.Number
            && ((org.example.objects.Number) element2).getValue() == 1 && element1 instanceof Variable) {
            return element1;
        } else if ((element1 instanceof org.example.objects.Number && ((org.example.objects.Number) element1).getValue() == 0)
            || (element2 instanceof org.example.objects.Number && ((org.example.objects.Number) element2).getValue() == 0)) {
            return new org.example.objects.Number(0);
        } else if (element1 instanceof org.example.objects.Number && element2 instanceof org.example.objects.Number) {
            return new org.example.objects.Number(((org.example.objects.Number) element1).getValue() * ((Number) element2).getValue());
        }
        return new Mul(element1.simplification(), element2.simplification());
    }

    /**
     * Означивание переменных.
     */
    public int eval(String s) {
        return element1.eval(s) * element2.eval(s);
    }
}
