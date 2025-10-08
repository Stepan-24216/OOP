package org.example;

import java.util.ArrayList;

interface Graph {

    public void addEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
    public void deleteEdge(String nameEdge,Vertex firstVertex,Vertex secondVertex);
    public void addVertex(Vertex vertex);
    public void deleteVertex(Vertex vertex);
    public ArrayList<Vertex> getNeighbors(Vertex vertex);
//    public fileReader();

}
