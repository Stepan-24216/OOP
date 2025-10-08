package org.example;

import java.util.ArrayList;

interface Graph {
    public ArrayList<Vertex> getVertexList();
    public void addEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
    public void deleteEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
    public void addVertex(Vertex vertex);
    public void deleteVertex(Vertex vertex);
    public ArrayList<Vertex> getNeighbors(Vertex vertex);
    public void fileReader(String filePath);

}
