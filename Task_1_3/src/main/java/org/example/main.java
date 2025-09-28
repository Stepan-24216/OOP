package org.example;

import java.time.Period;
import java.util.Scanner;

public class main {


    public static void main(String[] args) {
        Expression inner = new Add(
                new Mul(new Variable("x"), new Variable("x")),
                new Mul(new Number(3), new Variable("x"))
        );
        Expression f = new Mul(inner, new Mul(inner, inner));
        (f.derivative("x")).print();
    }
}

