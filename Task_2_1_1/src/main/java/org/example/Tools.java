package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tools {
    public static boolean isPrime(int number) {
        if (number <= 1 || (number % 2 == 0 && number != 2)) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number)+1; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> readFile(String filePath) {
        ArrayList<Integer> numbers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String cleanedLine = line.replaceAll("[\\[\\]]", "");
                if (!cleanedLine.isEmpty()) {
                    parseLineToNumbers(cleanedLine, numbers);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
        return numbers;
    }

    public static void parseLineToNumbers(String line, ArrayList<Integer> numbers) {
        String[] tokens = line.split(",\\s+");
        for (String token : tokens) {
            try {
                int number = Integer.parseInt(token);
                numbers.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: " + token);
            }
        }
    }
}
