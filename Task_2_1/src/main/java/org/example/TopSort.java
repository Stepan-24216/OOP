package org.example;

import java.util.ArrayList;

public class TopSort {
    public static void DFS(Vertex vertex, ArrayList<Vertex> sortedList){
        if (vertex.getColor() != Color.BLACK){
            vertex.setColor(Color.GRAY);
            for (Edge edge: vertex.getEdges()){
                if (edge.getTarget().getColor() == Color.WHITE){
                    DFS(edge.getTarget(),sortedList);
                }
            }

            vertex.setColor(Color.BLACK);
            sortedList.add(vertex);
        }
    }
}
