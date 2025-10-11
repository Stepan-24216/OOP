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
        return target.getName();
    }

    public Vertex getTarget(){
        return target;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
       return nameEdge.equals(edge.nameEdge) && target.equals(edge.target);
    }
}
