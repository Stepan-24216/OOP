package org.example.objects;

import java.util.ArrayList;

/**
 * Класс вершины.
 */
public class Vertex {
    private final String vertexName;
    private final ArrayList<Edge> edges;
    private Color color;

    /**
     * Конструктор вершины.
     */
    public Vertex(String name) {
        this.vertexName = name;
        this.edges = new ArrayList<Edge>();
        this.color = Color.WHITE;
    }

    /**
     * Получить рёбра.
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Получить имя вершины.
     */
    public String getName() {
        return this.vertexName;
    }

    /**
     * Получить цвет вершины.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Установить цвет вершины.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Добавить ребро.
     */
    public void addEdge(String nameEdge, Vertex vertex) {
        if (!this.edges.contains(new Edge(nameEdge, vertex))) {
            this.edges.add(new Edge(nameEdge, vertex));
        }
    }

    /**
     * Удалить ребро.
     */
    public void deleteEdge(Edge edge) {
        if (this.edges.contains(edge)) {
            this.edges.remove(edge);
        }
    }

    /**
     * Метод equals для сравнения вершин по имени.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) obj;
        return vertexName != null && vertexName.equals(vertex.vertexName);
    }

}