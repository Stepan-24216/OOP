package org.example;

import java.time.Period;
import java.util.Scanner;

public class main {


    public static void main(String[] args){
        Parser parser = new Parser();
        Expression parsed = parser.parse("(5+3");
        parsed.print();
    }
}
