package org.example.graphstoragemethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.example.functional.ParseDataFile;
import org.example.functional.Sort;
import org.example.functional.TopSort;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

/**
 * Матрица инцидентности.
 */
public class IncidenceMatrix implements Graph {
    private final ArrayList<Edge> edgeList;
    private ArrayList<Vertex> vertexList;

    /**
     * Конструктор.
     */
    public IncidenceMatrix() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
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
        edgeList.removeIf(edge -> edge.getTarget().equals(v));
        for (Edge edge : v.getEdges()) {
            edgeList.removeIf(e -> e.equals(edge));
        }
        if (!vertexList.isEmpty()) {
            vertexList.remove(v);
        }

    }

    /**
     * Добавить ребро.
     */
    public void addEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex) {
        Edge newEdge = new Edge(nameEdge, secondVertex);
        firstVertex.addEdge(nameEdge, secondVertex);
        edgeList.add(newEdge);
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
        for (Edge edge : edgeList) {
            if (edge.getNameEdge().equals(nameEdge) && edge.getTarget().equals(secondVertex)) {
                edgeList.remove(edge);
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
     * Получение матрицы инцидентности.
     */
    protected String[][] getIncidenceMatrix() {
        String[][] incidenceMatrix = new String[vertexList.size() + 1][edgeList.size() + 1];
        for (int i = 0; i <= vertexList.size(); i++) {
            if (i == 0) {
                incidenceMatrix[i][0] = "";
            } else {
                incidenceMatrix[i][0] = vertexList.get(i - 1).getName();
            }
        }
        for (int j = 0; j <= edgeList.size(); j++) {
            if (j == 0) {
                incidenceMatrix[0][j] = "";
            } else {
                incidenceMatrix[0][j] = edgeList.get(j - 1).getNameEdge();
            }
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i - 1);
            for (int j = 1; j <= edgeList.size(); j++) {
                if (!vertex.getEdges().isEmpty()) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.equals(edgeList.get(j - 1))) {
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
     * Вывод списка вершин.
     */
    public void printVertexList(){
        for (Vertex vertex: vertexList){
            System.out.print(vertex.getName()+" ");
        }
        System.out.println();
    }

    /**
     * Вывод графа.
     */
    public void printGraph() {
        String[][] matrix = getIncidenceMatrix();
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
                System.out.printf(format, cell.isEmpty() ? "" : cell);
            }
            System.out.println();
        }
    }

    /**
     * Метод сортировки графа.
     */
    public void sort(Sort sort) {
        sort.sorted(this);
    }
}
