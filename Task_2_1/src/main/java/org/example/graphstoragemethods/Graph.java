package org.example.graphstoragemethods;

import java.util.ArrayList;
import org.example.objects.Vertex;

/**
 * Интерфейс граф.
 */
public interface Graph {
    ArrayList<Vertex> getVertexList();

    void setVertexList(ArrayList<Vertex> vertexList);

    void addVertex(Vertex vertex);

    void deleteVertex(Vertex vertex);

    void addEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex);

    void deleteEdge(String nameEdge, Vertex firstVertex, Vertex secondVertex);

    ArrayList<Vertex> getNeighbors(Vertex vertex);

    void printGraph();

    void fileReader(String filePath);

    void topologicalSort();
}
