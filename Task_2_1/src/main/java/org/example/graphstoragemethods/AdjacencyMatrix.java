package org.example.graphstoragemethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.example.functional.ParseDataFile;
import org.example.functional.TopSort;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

/**
 * Матрица смежности.
 */
public class AdjacencyMatrix implements Graph {
    private ArrayList<Vertex> vertexList;

    /**
     * Конструктор.
     */
    public AdjacencyMatrix() {
        vertexList = new ArrayList<>();
    }

    /**
     * Получение списка вершин.
     */
    public ArrayList<Vertex> getVertexList() {
        return vertexList;
    }

    /**
     * Присваивание списка вершин.
     */
    public void setVertexList(ArrayList<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    /**
     * Добавить вершину.
     */
    public void addVertex(Vertex vertex) {
        if (!vertexList.contains(vertex)) {
            vertexList.add(vertex);
        }
    }

    /**
     * Удаление вершины.
     */
    public void deleteVertex(Vertex v) {
        for (Vertex vertex : vertexList) {
            if (!vertex.getEdges().isEmpty()) {
                for (Edge edges : vertex.getEdges()) {
                    if (edges.getTarget().equals(v)) {
                        vertex.deleteEdge(edges);
                        break;
                    }
                }
            }
        }
        if (!vertexList.isEmpty()) {
            vertexList.remove(v);
        }
    }

    /**
     * Добавить ребро.
     */
    public void addEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex) {
        firstVertex.addEdge(nameEdge, secondVertex);
    }

    /**
     * Удалить ребро.
     */
    public void deleteEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex) {
        for (Edge edges : firstVertex.getEdges()) {
            if (edges.getTarget().equals(secondVertex)) {
                firstVertex.deleteEdge(edges);
                break;
            }
        }
    }

    /**
     * Получение смежных вершин.
     */
    public ArrayList<Vertex> getNeighbors(Vertex vertex) {
        ArrayList<Vertex> adjacent = new ArrayList<>();
        if (vertexList.contains(vertex)) {
            for (Edge edge : vertex.getEdges()) {
                adjacent.add(edge.getTarget());
            }
        }
        return adjacent;
    }

    /**
     * Получение матрицы смежности.
     */
    public String[][] getAdjacencyMatrix() {
        String[][] incidenceMatrix = new String[vertexList.size() + 1][vertexList.size() + 1];
        for (int i = 0; i <= vertexList.size(); i++) {
            if (i == 0) {
                incidenceMatrix[i][0] = "";
            } else {
                incidenceMatrix[i][0] = vertexList.get(i - 1).getName();
                incidenceMatrix[0][i] = vertexList.get(i - 1).getName();
            }
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i - 1);
            for (int j = 1; j <= vertexList.size(); j++) {
                if (!vertex.getEdges().isEmpty()) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getTarget().equals(vertexList.get(j - 1))) {
                            incidenceMatrix[i][j] = "1";
                            break;
                        } else {
                            incidenceMatrix[i][j] = "0";
                        }
                    }
                } else {
                    incidenceMatrix[i][j] = "0";
                }
            }
        }
        return incidenceMatrix;
    }

    /**
     * Вывод графа.
     */
    public void printGraph() {
        String[][] matrix = getAdjacencyMatrix();
        int maxLen = 1;
        for (String[] row : matrix) {
            for (String cell : row) {
                if (!cell.isEmpty() && cell.length() > maxLen) {
                    maxLen = cell.length();
                }
            }
        }
        String format = "%-" + (maxLen + 2) + "s";
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.printf(format, cell == null ? "" : cell);
            }
            System.out.println();
        }
    }

    /**
     * Чтение файла фиксированного формата.
     */
    public void fileReader(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    ParseDataFile.parseData(line, this, false);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }

    /**
     * Топологическая сортировка графа.
     */
    public void topologicalSort() {
        ArrayList<Vertex> sortedList = new ArrayList<>();
        for (Vertex vertex : vertexList) {
            vertex.setColor(Color.WHITE);
        }
        for (Vertex vertex : vertexList) {
            TopSort.depthFirstSearch(vertex, sortedList);
        }
        Collections.reverse(sortedList);
        this.setVertexList(sortedList);
    }
}
