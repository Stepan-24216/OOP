package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestParse {
    @Test
    void testFileReader() {
        Graph graph2 = new AdjacencyMatrix();
        graph2.fileReader("src/main/java/org/example/test.txt");
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
