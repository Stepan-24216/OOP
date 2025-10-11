package org.example;

import java.util.ArrayList;

public class Vertex {
    private final String vertexName;
    private final ArrayList<Edge> edges;
    private Color color;

    Vertex(String name) {
        this.vertexName = name;
        this.edges = new ArrayList<Edge>();
        this.color = Color.WHITE;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getName() {
        return this.vertexName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addEdge(String nameEdge, Vertex vertex) {
        if (!this.edges.contains(new Edge(nameEdge, vertex))){
            this.edges.add(new Edge(nameEdge, vertex));
        }
    }

    public void deleteEdge(Edge edge) {
        if (this.edges.contains(edge)) {
            this.edges.remove(edge);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return vertexName != null && vertexName.equals(vertex.vertexName);
    }

}