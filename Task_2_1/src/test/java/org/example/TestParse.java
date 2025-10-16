package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.functional.TopSort;
import org.junit.jupiter.api.Test;
import org.example.functional.ParseDataFile;
import org.example.graphstoragemethods.AdjacencyMatrix;
import org.example.graphstoragemethods.Graph;
import org.example.objects.Vertex;

/**
 * Тесты для парсинга файлов с данными.
 */
public class TestParse {
    @Test
    void testFileReaderCase1() {
        Graph graph2 = new AdjacencyMatrix();
        graph2.fileReader("src/test/java/org/example/test1.txt");
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        TopSort sorted = new TopSort();
        sorted.sorted(graph2);
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }

    @Test
    void testFileReaderCase2() {
        Graph graph2 = new AdjacencyMatrix();
        graph2.fileReader("src/test/java/org/example/test1.txt");
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        TopSort sorted = new TopSort();
        sorted.sorted(graph2);
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }

    @Test
    void testParseNoEdge() {
        Graph graph2 = new AdjacencyMatrix();
        String[] data = {
            "E F",
            "B F",
            "B C",
            "A B",
            "C E",
            "C D",
            "A E"
        };
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        for (String line : data) {
            ParseDataFile.parseData(line, graph2, false);
        }
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        TopSort sorted = new TopSort();
        sorted.sorted(graph2);
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }

    @Test
    void testParseEdge() {
        Graph graph2 = new AdjacencyMatrix();
        String[] data = {
            "E F edge5",
            "B F edge6",
            "B C edge2",
            "A B edge1",
            "C E edge7",
            "C D edge3",
            "A E edge4"
        };
        String[] cur2 = {"E", "F", "B", "C", "A", "D"};
        for (String line : data) {
            ParseDataFile.parseData(line, graph2, false);
        }
        int i = 0;
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(cur2[i], vertex.getName());
            i++;
        }
        i = 0;
        TopSort sorted = new TopSort();
        sorted.sorted(graph2);
        String[] sort2 = {"A", "B", "C", "D", "E", "F"};
        for (Vertex vertex : graph2.getVertexList()) {
            assertEquals(sort2[i], vertex.getName());
            i++;
        }
    }
}
