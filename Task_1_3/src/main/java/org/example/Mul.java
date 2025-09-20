package org.example;

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

    public String toString() {
        return "(" + element1 + " * " + element2 + ")";
    }

    public Expression derivative(String variable) {
        if (!(element1 instanceof Number) && element2 instanceof Number) {
            return new Mul(element1.derivative(variable), element2);
        } else if (element1 instanceof Number && !(element2 instanceof Number)) {
            return new Mul(element1, element2.derivative(variable));
        }
        return new Add(new Mul(element1.derivative(variable), element2),
                new Mul(element1, element2.derivative(variable)));
    }

    public int eval(String s) {
        return element1.eval(s) * element2.eval(s);
    }
}
