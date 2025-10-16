package org.example.functional;

import java.util.ArrayList;
import java.util.Collections;
import org.example.graphstoragemethods.Graph;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

/**
 * Поиск в глубину для топологической сортировки.
 */
public class TopSort implements Sort {
    /**
     * Поиск в глубину.
     */
    public static void depthFirstSearch(Vertex vertex, ArrayList<Vertex> sortedList) {
        if (vertex.getColor() != Color.BLACK) {
            vertex.setColor(Color.GRAY);
            for (Edge edge : vertex.getEdges()) {
                if (edge.getTarget().getColor() == Color.WHITE) {
                    depthFirstSearch(edge.getTarget(), sortedList);
                }
            }

            vertex.setColor(Color.BLACK);
            sortedList.add(vertex);
        }
    }

    /**
     * Топологическая сортировка графа.
     */
    public void sorted(Graph graph) {
        ArrayList<Vertex> sortedList = new ArrayList<>();
        for (Vertex vertex : graph.getVertexList()) {
            vertex.setColor(Color.WHITE);
        }
        for (Vertex vertex : graph.getVertexList()) {
            TopSort.depthFirstSearch(vertex, sortedList);
        }
        Collections.reverse(sortedList);
        graph.setVertexList(sortedList);
    }
}
