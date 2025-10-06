package org.example;

import java.util.ArrayList;

public class Vertex {
    String vertexName;
    ArrayList<Edge> edges;

    Vertex(String name) {
        this.vertexName = name;
        this.edges = new ArrayList<Edge>();
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }
    
    public String getName() {
       return this.vertexName;
    }

    public void addEdge(String nameEdge, Vertex vertex) {
        this.edges.add(new Edge(nameEdge, vertex));
    }

    public void deleteEdge(Edge edge) {
        if (this.edges.contains(edge)){
            this.edges.remove(edge);
        }
    }

    public boolean equals(Vertex vertex) {
        if (this == vertex) return true;
        if (vertex == null) return false;
        return vertexName.equals(vertex.vertexName);
    }

//    public void deleteVertex(){
//        boolean flag = false;
//        for (Vertex neighbor : vertexList) {
//            if (this == neighbor){
//                flag = true;
//            } else {
//                neighbor.adjacencyList.adjacencyList.remove(this);
//            }
//        }
//        if (flag){
//            adjacencyList.adjacencyList.remove(this);
//        }
//        adjacencyList.adjacencyList.clear();
//    }
}