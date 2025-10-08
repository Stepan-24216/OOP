package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjacencyMatrix implements Graph {
    ArrayList<Vertex> vertexList;

    public ArrayList<Vertex> getVertexList(){
        return vertexList;
    }

    public AdjacencyMatrix() {
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

    public String[][] getAdjacencyMatrix(){
        String[][] IncidenceMatrix = new String[vertexList.size()+1][vertexList.size()+1];
        for (int i = 0; i <= vertexList.size(); i++) {
            if (i == 0) {
                IncidenceMatrix[i][0] = "";
            } else {
                IncidenceMatrix[i][0] = vertexList.get(i-1).vertexName;
                IncidenceMatrix[0][i] = vertexList.get(i-1).vertexName;
            }
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i-1);
            for (int j = 1; j <= vertexList.size(); j++) {
                if (!vertex.edges.isEmpty()) {
                    for (Edge edge: vertex.edges) {
                        if (edge.getTarget().equals(vertexList.get(j-1))) {
                            IncidenceMatrix[i][j] = "1";
                            break;
                        } else {
                            IncidenceMatrix[i][j] = "0";
                        }
                    }
                } else {
                    IncidenceMatrix[i][j] = "0";
                }
            }
        }
        return IncidenceMatrix;
    }

    public void printAdjacencyMatrix() {
        String[][] matrix = getAdjacencyMatrix();
        int maxLen = 1;
        for (String[] row : matrix) {
            for (String cell : row) {
                if (cell != null && cell.length() > maxLen) {
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
