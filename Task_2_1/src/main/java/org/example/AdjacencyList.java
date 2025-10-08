package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdjacencyList implements Graph{
    ArrayList<Vertex> vertexList;

    public ArrayList<Vertex> getVertexList(){
        return vertexList;
    }

    public AdjacencyList() {
        vertexList = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        if (!vertexList.contains(vertex)) {
            vertexList.add(vertex);
        }
    }

    public void deleteVertex(Vertex v) {
        for (Vertex vertex : vertexList) {
            if (vertex.edges != null) {
                for (Edge edges : vertex.edges) {
                    if (edges.getTarget().equals(v)) {
                        vertex.deleteEdge(edges);
                        break;
                    }
                }
            }
        }
        if (vertexList != null) {
            vertexList.remove(v);
        }
    }

   public void addEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex) {
       if (firstVertex.edges == null) {
           firstVertex.edges = new ArrayList<>();
       }
       firstVertex.addEdge(nameEdge, secondVertex);
   }

    public void deleteEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex){
        for (Edge edges : firstVertex.edges) {
            if (edges.getTarget().equals(secondVertex)) {
                firstVertex.deleteEdge(edges);
                break;
            }
        }
    }

    public ArrayList<Vertex> getNeighbors(Vertex vertex) {
        ArrayList<Vertex> adjacent = new ArrayList<>();
        if (vertexList.contains(vertex)) {
            for (Edge edge : vertex.getEdges()) {
                adjacent.add(edge.getTarget());
            }
        }
        return adjacent;
    }

    public void printAdjacentVertices(Vertex vertex){
        System.out.print(vertex.vertexName + ": ");
        if (vertex.edges != null) {
            for (Edge neighbor : vertex.edges) {
                System.out.print(neighbor.getNameVertex() + " ");
            }
        }
        System.out.println();
    }

    public void printAdjacencyList() {
        if (vertexList == null || vertexList.isEmpty()) {
            System.out.println("Список вершин пуст");
            return;
        }
        for (Vertex vertex : vertexList) {
            if (vertex == null || vertex.vertexName == null) {
                System.out.println("Вершина не инициализирована");
                continue;
            }
            printAdjacentVertices(vertex);
        }
    }

    public void fileReader(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    ParseDataFile.parseData(line,this,false);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }
}
