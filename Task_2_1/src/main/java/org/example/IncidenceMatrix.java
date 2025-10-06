package org.example;

import java.util.ArrayList;

public class IncidenceMatrix {
    ArrayList<Vertex> vertexList;
    ArrayList<Edge> edgeList;

    public IncidenceMatrix() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        if (!vertexList.contains(vertex)) {
            vertexList.add(vertex);
        }
    }

    public void deleteVertex(Vertex v) {
        for (Vertex vertex : vertexList) {
            if (!vertex.edges.isEmpty()) {
                for (Edge edges : vertex.edges) {
                    if (edges.getTarget().equals(v)) {
                        vertex.deleteEdge(edges);
                        break;
                    }
                }
            }
        }
        edgeList.removeIf(edge -> edge.getTarget().equals(v));
        for (Edge edge:v.edges){
            edgeList.removeIf(e -> e.equals(edge));
        }
        if (!vertexList.isEmpty()) {
            vertexList.remove(v);
        }

    }

    public void addEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex) {
        Edge newEdge = new Edge(nameEdge, secondVertex);
        firstVertex.addEdge(nameEdge, secondVertex);
        edgeList.add(newEdge);
    }

    public void deleteEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex){
        for (Edge edges : firstVertex.edges) {
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

    public ArrayList<Vertex> getNeighbors(Vertex vertex) {
        ArrayList<Vertex> adjacent = new ArrayList<>();
        if (vertexList.contains(vertex)) {
            for (Edge edge : vertex.getEdges()) {
                adjacent.add(edge.getTarget());
            }
        }
        return adjacent;
    }

    public String[][] getIncidenceMatrix(){
        String[][] IncidenceMatrix = new String[vertexList.size()+1][edgeList.size()+1];
        for (int i = 0; i <= vertexList.size(); i++) {
            if (i == 0) {
                IncidenceMatrix[i][0] = "";
            } else {
                IncidenceMatrix[i][0] = vertexList.get(i-1).vertexName;
            }
        }
        for (int j = 0; j <= edgeList.size(); j++) {
            if (j == 0) {
                IncidenceMatrix[0][j] = "";
            } else {
                IncidenceMatrix[0][j] = edgeList.get(j-1).nameEdge;
            }
        }
        for (int i = 1; i <= vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i-1);
            for (int j = 1; j <= edgeList.size(); j++) {
                if (!vertex.edges.isEmpty()) {
                    for (Edge edge: vertex.edges) {
                        if (edge.equals(edgeList.get(j-1))) {
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

    public void printIncidenceMatrix() {
        String[][] matrix = getIncidenceMatrix();
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

}
