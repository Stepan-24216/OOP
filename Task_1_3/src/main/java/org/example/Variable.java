package org.example;

public class Variable extends Expression{
    private String var;

    public Variable(String name) {
        this.var = name;
    }

    public String toString(){
        return var;
    }

    public Expression derivative(String variable){
        return new Number(this.var.equals(variable) ? 1 : 0);
    }
}
