package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Алгоритм Боера Мура.
 */
public class AlgorithmBoyerMura {
    private long globalOffset; // Для перекрывающегося чтения
    private RandomAccessFile raf;
    private String currentChunk;

    /**
     * Функция поиска.
     */
    public Set<Integer> find(String pattern, String filePath) throws IOException{
        Set<Integer> results = new TreeSet<>();
        int[] badCharTable = buildBadCharTable(pattern);
        int patternLength = pattern.length();
        raf = new RandomAccessFile(filePath, "r");

        globalOffset = 0;
        currentChunk = readChunk(patternLength * 2 + patternLength);

        while (currentChunk != null) {
            List<Integer> chunkResults = boyerMooreSearch(currentChunk, pattern, badCharTable);

            for (int pos : chunkResults) {
                results.add((int) globalOffset - currentChunk.length() + pos);
            }

            if (currentChunk.length() > patternLength) {
                globalOffset -= patternLength;
            }
            currentChunk =
                readChunk(patternLength * 2 + patternLength); // С учётом перекрытия
        }

        close();

        return results;
    }

    /**
     * Функция поиска.
     */
    private List<Integer> boyerMooreSearch(String text, String pattern, int[] badCharTable) {
        List<Integer> results = new ArrayList<>();
        int patternLength = pattern.length();
        int textLength = text.length();

        int shift = 0;
        while (shift <= textLength - patternLength) {
            int j = patternLength - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                results.add(shift);
                shift += (shift + patternLength < textLength) ?
                    patternLength - badCharTable[text.charAt(shift + patternLength)] : 1;
            } else {
                shift += Math.max(1, j - badCharTable[text.charAt(shift + j)]);
            }
        }

        return results;
    }

    /**
     * Алгоритм Боера Мура.
     */
    private int[] buildBadCharTable(String pattern) {
        int[] table = new int[256];
        Arrays.fill(table, -1);

        for (int i = 0; i < pattern.length(); i++) {
            table[pattern.charAt(i)] = i;
        }
        return table;
    }

    /**
     * Чтение куска текста.
     */
    private String readChunk(int chunkSize) {
        try  {
            raf.seek(globalOffset);

            byte[] buffer = new byte[chunkSize];
            int bytesRead = raf.read(buffer);

            if (bytesRead <= 0) {
                return null;
            }

            globalOffset += bytesRead;
            return new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.printf("Ошибка чтения: %s%n", e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException e) {
            System.err.printf("Ошибка закрытия файла: %s%n", e.getMessage());
        }
    }
}