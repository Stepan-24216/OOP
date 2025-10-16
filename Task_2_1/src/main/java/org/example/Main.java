package org.example;

import org.example.functional.TopSort;
import org.example.graphstoragemethods.AdjacencyList;
import org.example.objects.Vertex;

public class Main {
    public static void main(String[] args) {
        AdjacencyList graph = new AdjacencyList();
        graph.fileReader("src/test/java/org/example/test1.txt");
//        graph.printVertexList();
//        graph.printGraph();
//        graph.addVertex(new Vertex("Gg"));
//        graph.addEdge("haha",new Vertex("Gg"),new Vertex("A"));
        AdjacencyList graph2 = new AdjacencyList();
        graph2.fileReader("src/test/java/org/example/test2.txt");
        System.out.print(graph.equalsGraph(graph2));
    }
}
