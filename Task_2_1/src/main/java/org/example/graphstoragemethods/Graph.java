package org.example.graphstoragemethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.functional.ParseDataFile;
import org.example.functional.Sort;
import org.example.functional.TopSort;
import org.example.objects.Edge;
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

    void printVertexList();

    void printGraph();

    default void fileReader(String filePath) {
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

    void sort(Sort sort);

    default boolean equalsGraph(Graph other) {
        if (this.getVertexList().size() != other.getVertexList().size()) {
            return false;
        }

        TopSort sort = new TopSort();

        this.sort(sort);
        other.sort(sort);

        List<Vertex> otherVertexList = other.getVertexList();
        for (int i = 0; i < this.getVertexList().size(); i++) {
            Vertex thisVertex = this.getVertexList().get(i);
            Vertex otherVertex = otherVertexList.get(i);

            if (!thisVertex.getName().equals(otherVertex.getName())) {
                return false;
            }

            List<Edge> thisEdges = thisVertex.getEdges();
            List<Edge> otherEdges = otherVertex.getEdges();

            if (thisEdges.size() != otherEdges.size()) {
                return false;
            }

            thisEdges.sort(java.util.Comparator.comparing(Edge::getNameVertex));
            otherEdges.sort(java.util.Comparator.comparing(Edge::getNameVertex));

            for (int j = 0; j < thisEdges.size(); j++) {
                Edge thisEdge = thisEdges.get(j);
                Edge otherEdge = otherEdges.get(j);

                if (!thisEdge.equals(otherEdge)) {
                    return false;
                }
            }
        }

        return true;
    }
}
