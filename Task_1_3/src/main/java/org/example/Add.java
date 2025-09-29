package org.example;

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

    public String toString() {
        return "(" + element1 + " + " + element2 + ")";
    }

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

    public Expression simplification() {
        if ((element1 instanceof Number && element2 instanceof Number)
            && ((Number) element1).getValue() == 0 && ((Number) element2).getValue() == 0) {
            return new Number(0);
        } else if ((element1 instanceof Number)
            && ((Number) element1).getValue() == 0) {
            return element2;
        } else if ((element2 instanceof Number)
            && ((Number) element2).getValue() == 0) {
            return element1;
        } else if (element1 instanceof Number && element2 instanceof Number) {
            return new Number(((Number) element1).getValue() + ((Number) element2).getValue());
        }
        return new Add(element1, element2);
    }

    public int eval(String s) {
        return element1.eval(s) + element2.eval(s);
    }
}
