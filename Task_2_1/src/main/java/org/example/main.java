package org.example;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Vertex x = new Vertex("1");
        Vertex y = new Vertex("2");
        Vertex z = new Vertex("3");
        Vertex c = new Vertex("1");

//        Vertex v = new Vertex("5");
//
        IncidenceMatrix list = new IncidenceMatrix();

        list.fileReader("src/main/java/org/example/test.txt");
//        list.addVertex(x);
//        list.addVertex(y);
//        list.addVertex(z);
//        list.addEdge("haha",x,y);
//        list.addEdge("hehe",y,z);
//        list.addEdge("hoho",x,z);
//        list.addEdge("hihi",x,x);
//        list.addEdge("lolo",z,y);
//
        list.printIncidenceMatrix();
//
//        list.deleteVertex(y);
//
//        list.printAdjacencyList();
//        z.printAdjacencyList();
//
//        x.deleteVertex();
//
//        z.printAdjacencyList();
    }
}