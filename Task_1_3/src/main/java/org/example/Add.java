package org.example;

import java.security.PublicKey;

public class Add extends Expression{
    Expression element1;
    Expression element2;

    public Add(Expression left, Expression right) {
        this.element1 = left;
        this.element2 = right;
    }
    public String toString(){
        return "(" + element1+" + "+ element2 + ")";
    }

    public Expression derivative(String variable){
        return new Add(element1.derivative(variable),element2.derivative(variable));
    }
}
