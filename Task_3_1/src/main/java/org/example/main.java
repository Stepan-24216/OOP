package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        String filePath = "/home/rend/java/OOP/Task_3_1/src/main/java/org/example/test";
        AlgorithmBoyerMura a = new AlgorithmBoyerMura();
        for (int num:a.find(filePath, "aba")){
            System.out.println(num);
        }
    }
}