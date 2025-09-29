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
        Expression mul1 = element1.derivative(variable);
        Expression mul2 = element2.derivative(variable);
        Expression summand1 = new Mul(element1.derivative(variable), element2);
        Expression summand2 = new Mul(element1, element2.derivative(variable));
//        if (mul1 instanceof Number && ((Number) mul1).getValue() == 1
//            && element2 instanceof Variable) {
//            summand1 = element2;
//        }
//        if (mul2 instanceof Number
//            && ((Number) mul2).getValue() == 1 && element1 instanceof Variable) {
//            summand2 = element1;
//        }
        return new Add(summand1, summand2);
    }

    public Expression simplification() {
        if ((element1 instanceof Number && ((Number) element1).getValue() == 1
            && element2 instanceof Variable)) {
            return element2;
        } else if (element2 instanceof Number
            && ((Number) element2).getValue() == 1 && element1 instanceof Variable) {
            return element1;
        } else if ((element1 instanceof Number && ((Number) element1).getValue() == 0)
            || (element2 instanceof Number && ((Number) element2).getValue() == 0)) {
            return new Number(0);
        } else if (element1 instanceof Number && element2 instanceof Number) {
            return new Number(((Number) element1).getValue() * ((Number) element2).getValue());
        }
        return new Mul(element1, element2);
    }

    public int eval(String s) {
        return element1.eval(s) * element2.eval(s);
    }
}
