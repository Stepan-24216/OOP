package org.example;

public class Edge {
    Vertex target;
    String nameEdge;

    Edge(String nameEdge, Vertex vertex) {
        this.nameEdge = nameEdge;
        this.target = vertex;
    }

    public String getNameEdge(){
        return nameEdge;
    }

    public String getNameVertex(){
        return target.vertexName;
    }

    public Vertex getTarget(){
        return target;
    }

    public boolean equals(Edge edge) {
        if (this == edge) return true;
        if (edge == null) return false;
        return nameEdge.equals(edge.nameEdge) && target.equals(edge.target);
    }
}
