package org.example.graphstoragemethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.example.functional.ParseDataFile;
import org.example.functional.Sort;
import org.example.functional.TopSort;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

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


    /**
     * Метод сортировки графа.
     */
    public void sort(Sort sort) {
        sort.sorted(this);
    }
}
