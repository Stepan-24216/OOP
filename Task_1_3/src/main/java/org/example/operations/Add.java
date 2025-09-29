package org.example.operations;

import org.example.objects.Expression;
import org.example.objects.Number;

/**
 * Реализация методов для операции сложения.
 */
public class Add extends Expression {
    Expression element1;
    Expression element2;

    public Add(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }

    /**
     * Преобразование в строку.
     */
    public String toString() {
        return "(" + element1 + " + " + element2 + ")";
    }

    /**
     * Производная для суммы.
     */
    public Expression derivative(String variable) {
        // Ну это упрощение наверное не нужно было делать, пусть пока что будет :)
        //Expression e1 = element1.derivative(variable);
        //Expression e2 = element2.derivative(variable);
        //if (e1 instanceof Number && e2 instanceof Number) {
        //Number num1 = (Number) e1;
        //Number num2 = (Number) e2;
        //if (num1.getValue() == 0 && num2.getValue() == 0) {
        //return new Number(0);
        //}
        //}
        return new Add(element1.derivative(variable), element2.derivative(variable));
    }

    /**
     * Упрощение выражения.
     */
    public Expression simplification() {
        if ((element1 instanceof org.example.objects.Number && element2 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue() == 0 && ((org.example.objects.Number) element2).getValue() == 0) {
            return new org.example.objects.Number(0);
        } else if ((element1 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element1).getValue() == 0) {
            return element2;
        } else if ((element2 instanceof org.example.objects.Number)
            && ((org.example.objects.Number) element2).getValue() == 0) {
            return element1;
        } else if (element1 instanceof org.example.objects.Number && element2 instanceof org.example.objects.Number) {
            return new org.example.objects.Number(((org.example.objects.Number) element1).getValue() + ((Number) element2).getValue());
        }
        return new Add(element1.simplification(), element2.simplification());
    }

    /**
     * Означивание для суммы.
     */
    public int eval(String s) {
        return element1.eval(s) + element2.eval(s);
    }
}
