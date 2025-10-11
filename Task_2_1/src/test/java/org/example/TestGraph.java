package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Тесты для различных представлений графа.
 */

public class TestGraph {
    /**
     * Вершины для тестировки.
     */
    Vertex vertexA = new Vertex("A");
    Vertex vertexB = new Vertex("B");
    Vertex vertexC = new Vertex("C");
    Vertex vertexD = new Vertex("D");
    Vertex vertexE = new Vertex("E");
    Vertex vertexF = new Vertex("F");


    /**
     * Тесты для Матрицы смежности.
     */
    @Test
    void testAdjacencyMatrix() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        AdjacencyMatrix graph = new AdjacencyMatrix();
        graph.addVertex(vertexF);
        graph.addVertex(vertexD);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexE);
        graph.addVertex(vertexA);

        graph.addEdge("edge1", vertexA, vertexB);
        graph.addEdge("edge2", vertexB, vertexC);
        graph.addEdge("edge3", vertexC, vertexD);
        graph.addEdge("edge4", vertexA, vertexE);
        graph.addEdge("edge5", vertexE, vertexF);
        graph.addEdge("edge6", vertexB, vertexF);
        graph.addEdge("edge7", vertexC, vertexE);
        int i = 0;
        String[] cur = {"F", "D", "B", "C", "E", "A"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(cur[i], vertex.getName());
            i++;
        }
        i = 0;
        graph.topologicalSort();
        String[] sort = {"A", "B", "C", "E", "D", "F"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(sort[i], vertex.getName());
            i++;
        }
        String[][] expectedMatrix = {
            {" ", "A", "B", "C", "D", "E", "F"},
            {"A", "0", "1", "0", "1", "0", "0"}, // A
            {"B", "0", "0", "1", "0", "0", "1"}, // B
            {"C", "0", "0", "0", "1", "1", "0"}, // C
            {"E", "0", "0", "0", "0", "0", "1"}, // E
            {"D", "0", "0", "0", "0", "0", "0"}, // D
            {"F", "0", "0", "0", "0", "0", "0"}  // F
        };
        String[][] matrix = graph.getAdjacencyMatrix();
        for (i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                assertEquals(expectedMatrix[i][j], matrix[i][j]);
            }
        }

        graph.deleteEdge("edge7", vertexC, vertexE);
        i = 0;
        graph.topologicalSort();
        String[] sortDelEge = {"A", "E", "B", "F", "C", "D"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(sortDelEge[i], vertex.getName());
            i++;
        }

        graph.printGraph();
        String output = outContent.toString();

        assertTrue(output.contains(
                  "   A  E  B  F  C  D  \n"
                + "A  0  1  1  0  0  0  \n"
                + "E  0  0  0  1  0  0  \n"
                + "B  0  0  0  1  1  0  \n"
                + "F  0  0  0  0  0  0  \n"
                + "C  0  0  0  0  0  1  \n"
                + "D  0  0  0  0  0  0  "));
    }

    /**
     * Тесты для Списка смежности.
     */
    @Test
    void testAdjacencyList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        AdjacencyList graph = new AdjacencyList();
        graph.addVertex(vertexF);
        graph.addVertex(vertexD);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexE);
        graph.addVertex(vertexA);

        graph.addEdge("edge1", vertexA, vertexB);
        graph.addEdge("edge2", vertexB, vertexC);
        graph.addEdge("edge3", vertexC, vertexD);
        graph.addEdge("edge4", vertexA, vertexE);
        graph.addEdge("edge5", vertexE, vertexF);
        graph.addEdge("edge6", vertexB, vertexF);
        graph.addEdge("edge7", vertexC, vertexE);
        int i = 0;
        String[] cur = {"F", "D", "B", "C", "E", "A"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(cur[i], vertex.getName());
            i++;
        }
        i = 0;
        graph.topologicalSort();
        String[] sort = {"A", "B", "C", "E", "D", "F"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(sort[i], vertex.getName());
            i++;
        }

        i = 0;
        int j = 0;
        String[][] adjacencyList = {
            {"A", "B", "E"},
            {"B", "C", "F"},
            {"C", "D", "E"},
            {"E", "F"},
            {"D"},
            {"F"}
        };
        for (Vertex vertex : graph.getVertexList()) {
            i = 0;
            for (Edge neighbor : vertex.getEdges()) {
                assertEquals(adjacencyList[j][i + 1], neighbor.getNameVertex());
                i++;
            }
            j++;
        }
        graph.deleteEdge("edge7", vertexC, vertexE);

        i = 0;
        j = 0;
        String[][] adjacencyList2 = {
            {"A", "B", "E"},
            {"B", "C", "F"},
            {"C", "D"},
            {"E", "F"},
            {"D"},
            {"F"}
        };
        for (Vertex vertex : graph.getVertexList()) {
            i = 0;
            for (Edge neighbor : vertex.getEdges()) {
                assertEquals(adjacencyList2[j][i + 1], neighbor.getNameVertex());
                i++;
            }
            j++;
        }

        graph.deleteVertex(vertexC);
        i = 0;
        j = 0;
        String[][] adjacencyList3 = {
            {"A", "B", "E"},
            {"B", "F"},
            {"E", "F"},
            {"D"},
            {"F"}
        };
        for (Vertex vertex : graph.getVertexList()) {
            i = 0;
            for (Edge neighbor : vertex.getEdges()) {
                assertEquals(adjacencyList3[j][i + 1], neighbor.getNameVertex());
                i++;
            }
            j++;
        }

        graph.printGraph();
        String output = outContent.toString();

        assertTrue(output.contains("A: B E \n"
                                + "B: F \n"
                                + "E: F \n"
                                + "D: \n"
                                + "F: "));
    }

    /**
     * Тесты для Матрицы инцидентности.
     */
    @Test
    void testIncidenceMatrix() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(vertexF);
        graph.addVertex(vertexD);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexE);
        graph.addVertex(vertexA);

        graph.addEdge("edge1", vertexA, vertexB);
        graph.addEdge("edge2", vertexB, vertexC);
        graph.addEdge("edge3", vertexC, vertexD);
        graph.addEdge("edge4", vertexA, vertexE);
        graph.addEdge("edge5", vertexE, vertexF);
        graph.addEdge("edge6", vertexB, vertexF);
        graph.addEdge("edge7", vertexC, vertexE);
        int i = 0;
        String[] cur = {"F", "D", "B", "C", "E", "A"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(cur[i], vertex.getName());
            i++;
        }
        i = 0;
        graph.topologicalSort();
        String[] sort = {"A", "B", "C", "E", "D", "F"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(sort[i], vertex.getName());
            i++;
        }
        String[][] expectedMatrix = {
            {" ", "edge1", "edge2", "edge3", "edge4", "edge5", "edge6", "edge7"},
            {"A", "1", "0", "0", "1", "0", "0", "0"},
            {"B", "0", "1", "0", "0", "0", "1", "0"},
            {"C", "0", "0", "1", "0", "0", "0", "1"},
            {"E", "0", "0", "0", "0", "1", "0", "0"},
            {"D", "0", "0", "0", "0", "0", "0", "0"},
            {"F", "0", "0", "0", "0", "0", "0", "0"}
        };
        String[][] matrix = graph.getIncidenceMatrix();
        for (i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                assertEquals(expectedMatrix[i][j], matrix[i][j]);
            }
        }

        graph.deleteEdge("edge7", vertexC, vertexE);
        i = 0;
        graph.topologicalSort();
        String[] sortDelEge = {"A", "E", "B", "F", "C", "D"};
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(sortDelEge[i], vertex.getName());
            i++;
        }

        // Удаление вершины
        graph.deleteVertex(vertexB);
        String[] afterDeleteVertex = {"A", "E", "F", "C", "D"};
        i = 0;
        for (Vertex vertex : graph.getVertexList()) {
            assertEquals(afterDeleteVertex[i], vertex.getName());
            i++;
        }

        // Добавление новой вершины и ребра
        Vertex vertexG = new Vertex("G");
        graph.addVertex(vertexG);
        graph.addEdge("edge8", vertexG, vertexA);
        assertTrue(graph.getVertexList().contains(vertexG));

        graph.printGraph();
        String output = outContent.toString();

        assertTrue(output.contains(
                "       edge3  edge4  edge5  edge8  \n"
                + "A      0      1      0      0      \n"
                + "E      0      0      1      0      \n"
                + "F      0      0      0      0      \n"
                + "C      1      0      0      0      \n"
                + "D      0      0      0      0      \n"
                + "G      0      0      0      1  "));
    }
}
