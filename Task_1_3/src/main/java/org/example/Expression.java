package org.example;

public abstract class Expression {
    public abstract Expression derivative(String variable);
//    public int eval(String s){
//        String[] parts = s.split(";");
//    }
    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }
}

