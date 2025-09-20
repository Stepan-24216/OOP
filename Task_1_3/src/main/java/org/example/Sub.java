package org.example;

public class Sub extends Expression {
    Expression element1;
    Expression element2;

    public Sub(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }

    public String toString() {
        return "(" + element1 + " - " + element2 + ")";
    }

    public Expression derivative(String variable) {
        return new Sub(element1.derivative(variable), element2.derivative(variable));
    }

    public int eval(String s) {
        return element1.eval(s) - element2.eval(s);
    }
}
