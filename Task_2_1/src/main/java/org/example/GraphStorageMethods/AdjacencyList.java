package org.example.GraphStorageMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.example.Objects.Color;
import org.example.Functional.ParseDataFile;
import org.example.Functional.TopSort;
import org.example.Objects.Edge;
import org.example.Objects.Vertex;

/**
 * Список смежности.
 */
public class AdjacencyList implements Graph {
    private ArrayList<Vertex> vertexList;

    /**
     * Конструктор.
     */
    public AdjacencyList() {
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
     * Вывод смежных вершин.
     */
    private void printAdjacentVertices(Vertex vertex) {
        System.out.print(vertex.getName() + ": ");
        if (!vertex.getEdges().isEmpty()) {
            for (Edge neighbor : vertex.getEdges()) {
                System.out.print(neighbor.getNameVertex() + " ");
            }
        }
        System.out.println();
    }

    /**
     * Вывод графа.
     */
    public void printGraph() {
        if (vertexList.isEmpty()) {
            System.out.println("Список вершин пуст");
            return;
        }
        for (Vertex vertex : vertexList) {
            if (vertex == null || vertex.getName() == null) {
                System.out.println("Вершина не инициализирована");
                continue;
            }
            printAdjacentVertices(vertex);
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
