package org.example.operations;

import org.example.objects.Expression;
import org.example.objects.Number;
import org.example.objects.Variable;

/**
 * Реализация методов для операции вычитания.
 */
public class Sub extends Expression {
    Expression element1;
    Expression element2;

    public Sub(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        return "(" + element1 + " - " + element2 + ")";
    }

    /**
     * Взятие производной.
     */
    public Expression derivative(String variable) {
        Expression e1 = element1.derivative(variable);
        Expression e2 = element2.derivative(variable);
        boolean isElement1Number = e1 instanceof Number;
        boolean isElement2Number = e2 instanceof Number;
        if (isElement1Number && isElement2Number) {
            int value1 = ((Number) e1).getValue();
            int value2 = ((Number) e2).getValue();
            if (value1 == value2) {
                return new Number(0);
            } else if (value1 == 1 && value2 == 0) {
                return new Number(1);
            }
        }
        return new Sub(element1.derivative(variable), element2.derivative(variable));
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        boolean isElement1Number = element1 instanceof Number;
        boolean isElement2Number = element2 instanceof Number;

        if (isElement1Number && isElement2Number) {
            int value1 = ((Number) element1).getValue();
            int value2 = ((Number) element2).getValue();

            if (value1 == value2) {
                return new Number(0);
            }
            if (value2 == 0) {
                return element1;
            }
            return new Number(value1 - value2);
        }

        if (isElement2Number && ((Number) element2).getValue() == 0) {
            return element1;
        }
        if (isElement1Number && ((Number) element1).getValue() == 0
            && element2 instanceof Variable) {
            return new Variable('-' + element2.toString());
        }
        return new Sub(element1.simplification(), element2.simplification());
    }

    /**
     * Означивание переменных.
     */
    public int eval(String s) {
        return element1.eval(s) - element2.eval(s);
    }
}
