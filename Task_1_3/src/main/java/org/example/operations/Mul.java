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
        boolean isElement1Number = element1 instanceof Number;
        boolean isElement2Number = element2 instanceof Number;
        if (!isElement1Number && isElement2Number) {
            return new Mul(element1.derivative(variable), element2);
        } else if (isElement1Number && !isElement2Number) {
            return new Mul(element1, element2.derivative(variable));
        }
        Expression summand1 = new Mul(element1.derivative(variable), element2);
        Expression summand2 = new Mul(element1, element2.derivative(variable));
        return new Add(summand1, summand2);
    }

    /**
     * Упрощение выражения.
     */
     public Expression simplification() {
        boolean isElement1Number = element1 instanceof Number;
        boolean isElement2Number = element2 instanceof Number;

        if (isElement1Number && isElement2Number){
            int value1 = ((Number) element1).getValue();
            int value2 = ((Number) element2).getValue();

            if (value1 == 0 || value2 == 0) return new Number(0);
            if (value1 == 1) return element2;
            if (value2 == 1) return element1;
            return new Number(value1 * value2);
        }

        if (isElement1Number && ((Number) element1).getValue() == 1) {
            return element2;
        }
        if (isElement2Number && ((Number) element2).getValue() == 1) {
            return element1;
        }
        if (isElement1Number && ((Number) element1).getValue() == 0) {
            return new Number(0);
        }
        if (isElement2Number && ((Number) element2).getValue() == 0){
            return new Number(0);
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
