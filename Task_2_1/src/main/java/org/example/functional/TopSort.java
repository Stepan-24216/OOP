package org.example.functional;

import java.util.ArrayList;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

/**
 * Поиск в глубину для топологической сортировки.
 */
public class TopSort {
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
}
