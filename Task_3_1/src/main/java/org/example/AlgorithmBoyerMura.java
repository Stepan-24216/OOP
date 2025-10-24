package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AlgorithmBoyerMura {
    private Scanner scanner;
    private int lengthString;
    private int lengthSubstrings;
    private int indexSubstrings;
    private int indexString;
    private String filePathString;
    private String fileString;
    private String subStrings;

    public ArrayList<Integer> find(String filePathString, String subStrings){
        ArrayList<Integer> result = new ArrayList<>();
        int indicator = 0;
        this.filePathString = filePathString;
        this.subStrings = subStrings;
        this.lengthSubstrings = subStrings.length();
        nextString();
        while (this.fileString != null) {
            this.lengthString = this.fileString.length();
            this.indexString = 0;
            while (this.indexString <= this.lengthString - this.lengthSubstrings) {
                this.indexSubstrings = this.lengthSubstrings - 1;
                while (this.indexSubstrings >= 0 && this.subStrings.charAt(this.indexSubstrings) == this.fileString.charAt(this.indexString + this.indexSubstrings)) {
                    this.indexSubstrings--;
                }
                if (this.indexSubstrings < 0) {
                    result.add(indicator + this.indexString);
                    this.indexString += (this.indexString + this.lengthSubstrings < this.lengthString) ? this.lengthSubstrings - shift(this.fileString.substring(this.indexString + this.lengthSubstrings, this.indexString + this.lengthSubstrings + 1)) : 1;
                } else {
                    this.indexString += Math.max(1, this.indexSubstrings - shift(String.valueOf(this.fileString.charAt(this.indexString + this.indexSubstrings))));
                }
            }
            indicator += this.lengthString - this.lengthSubstrings + 1;
            nextString();
        }
        return result;
    }

   private String readFile(String filePathString,int bufferSize) {
        StringBuilder sb = new StringBuilder();
        try (java.io.FileReader fr = new java.io.FileReader(filePathString);
             BufferedReader reader = new BufferedReader(fr)) {

            if (this.indexString > 0) {
                long toSkip = this.indexString;
                while (toSkip > 0) {
                    long skipped = fr.skip(toSkip);
                    if (skipped <= 0) break;
                    toSkip -= skipped;
                }
            }

            char[] buffer = new char[bufferSize];
            int charsRead;
            int counter = 0;
            while (((charsRead = reader.read(buffer)) != -1) && counter <= bufferSize * 2 - 1) {
                sb.append(buffer, 0, charsRead);
                counter += charsRead;
            }
            if (counter == 0) {
                return null;
            }


            this.indexString += counter;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private int shift(String s){
        return 0;
    }

    private void nextString(){
        if (this.indexString >= this.lengthString - this.lengthSubstrings - 1) {
            this.fileString = fileString.substring(indexString) +
                readFile(this.filePathString, this.lengthSubstrings * 2);
            this.lengthString = this.fileString.length();
            this
        }
    }
}
