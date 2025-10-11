package org.example;

import java.util.ArrayList;

/**
 * Поиск в глубину для топологической сортировки.
 */
public class TopSort {
    /**
     * Поиск в глубину.
     */
    public static void DFS(Vertex vertex, ArrayList<Vertex> sortedList) {
        if (vertex.getColor() != Color.BLACK) {
            vertex.setColor(Color.GRAY);
            for (Edge edge : vertex.getEdges()) {
                if (edge.getTarget().getColor() == Color.WHITE) {
                    DFS(edge.getTarget(), sortedList);
                }
            }

            vertex.setColor(Color.BLACK);
            sortedList.add(vertex);
        }
    }
}
