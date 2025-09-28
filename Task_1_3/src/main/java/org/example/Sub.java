package org.example;

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

    public String toString() {
        return "(" + element1 + " - " + element2 + ")";
    }

    public Expression derivative(String variable) {
        Expression e1 = element1.derivative(variable);
        Expression e2 = element2.derivative(variable);
        if (e1 instanceof Number && e2 instanceof Number){
            Number num1 = (Number)e1;
            Number num2 = (Number)e2;
            if ((num1.getValue() == 0 && num2.getValue() == 0)
                || (num1.getValue() == 1 && num2.getValue() == 1)){
                return new Number(0);
            }
            else if (num1.getValue() == 1 && num2.getValue() == 0){
                return new Number(1);
            }
        }
        return new Sub(element1.derivative(variable), element2.derivative(variable));
    }

    public int eval(String s) {
        return element1.eval(s) - element2.eval(s);
    }
}
