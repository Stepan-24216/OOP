package org.example;

import java.util.ArrayList;

public interface Graph {
     void addEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
     void deleteEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
     void addVertex(Vertex vertex);
     void deleteVertex(Vertex vertex);
     ArrayList<Vertex> getNeighbors(Vertex vertex);
     void fileReader(String filePath);

    ArrayList<Vertex> getVertexList();
    void setVertexList(ArrayList<Vertex> vertexList);
    void printGraph();
    void topologicalSort();
}
