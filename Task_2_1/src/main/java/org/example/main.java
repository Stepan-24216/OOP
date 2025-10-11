package org.example;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");
        Vertex E = new Vertex("E");
        Vertex F = new Vertex("F");
        IncidenceMatrix graph = new IncidenceMatrix();
        graph.addVertex(F);
        graph.addVertex(D);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(E);
        graph.addVertex(A);

        graph.addEdge("edge1", A, B);
        graph.addEdge("edge2", B, C);
        graph.addEdge("edge3", C, D);
        graph.addEdge("edge4", A, E);
        graph.addEdge("edge5", E, F);
        graph.addEdge("edge6", B, F);
        graph.addEdge("edge7", C, E);
        graph.topologicalSort();
        graph.printGraph();
        graph.deleteEdge("edge7",C,E);
        graph.printGraph();
    }
}