package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для парсинга файлов с данными.
 */
public class TestParse {
    @Test
    void testFileReaderCase1() {
        Graph graph2 = new AdjacencyMatrix();
        graph2.fileReader("src/main/java/org/example/test1.txt");
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        graph2.topologicalSort();
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }

    @Test
    void testFileReaderCase2() {
        Graph graph2 = new AdjacencyMatrix();
        graph2.fileReader("src/main/java/org/example/test2.txt");
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        graph2.topologicalSort();
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }
}
