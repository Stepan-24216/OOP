package org.example;

public class Number extends Expression{
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public Expression derivative(String variable){
        return new Number(0); // производная константы это нуль
    }

    public String toString(){
        return String.valueOf(value);
    }

    public int eval(String s){
        return value;
    }
}
