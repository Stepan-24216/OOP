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
        if (e1 instanceof Number num1 && e2 instanceof Number num2) {
            if ((num1.getValue() == 0 && num2.getValue() == 0)
                || (num1.getValue() == 1 && num2.getValue() == 1)) {
                return new org.example.objects.Number(0);
            } else if (num1.getValue() == 1 && num2.getValue() == 0) {
                return new org.example.objects.Number(1);
            }
        }
        return new Sub(element1.derivative(variable), element2.derivative(variable));
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        if (((element1 instanceof org.example.objects.Number
            && element2 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue() == 0
            && ((org.example.objects.Number) element2).getValue() == 0)
            || ((element1 instanceof org.example.objects.Number
            && element2 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue()
            == ((org.example.objects.Number) element2).getValue())) {
            return new org.example.objects.Number(0);
        } else if ((element1 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue() == 0) {
            if (element2 instanceof org.example.objects.Number) {
                return new org.example.objects.Number(
                    -((org.example.objects.Number) element2).getValue());
            } else if (element2 instanceof Variable) {
                return new Variable('-' + (element2).toString());
            }
        } else if ((element2 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element2).getValue() == 0) {
            return element1;
        } else if (element1 instanceof org.example.objects.Number
            && element2 instanceof org.example.objects.Number) {
            return new org.example.objects.Number(
                ((org.example.objects.Number) element1).getValue()
                    - ((Number) element2).getValue());
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
