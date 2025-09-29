package org.example;

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

    public String toString() {
        return "(" + element1 + " / " + element2 + ")";
    }

    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(element1.derivative(variable), element2),
            new Mul(element1, element2.derivative(variable))), new Mul(element2, element2));
    }

    public Expression simplification() {
        if ((element1 instanceof Number
            && element2 instanceof Number)) {
            return new Number(((Number) element1).getValue() / ((Number) element2).getValue());
        } else if (element2 instanceof Number && ((Number) element2).getValue() == 1) {
            return element1;
        } else if ((element1 instanceof Number)
            && ((Number) element1).getValue() == 0) {
            return new Number(0);
        }
        return new Div(element1, element2);
    }

    public int eval(String s) {
        return element1.eval(s) / element2.eval(s);
    }
}
