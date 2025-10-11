package org.example;

/**
 * Класс ребра.
 */
public class Edge {
    Vertex target;
    String nameEdge;

    /**
     * Конструктор ребра.
     */
    Edge(String nameEdge, Vertex vertex) {
        this.nameEdge = nameEdge;
        this.target = vertex;
    }

    /**
     * Получить имя ребра.
     */
    public String getNameEdge() {
        return nameEdge;
    }

    /**
     * Получить имя вершины, на которую указывает ребро.
     */
    public String getNameVertex() {
        return target.getName();
    }

    /**
     * Получить вершину, на которую указывает ребро.
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * Метод equals для сравнения рёбер по имени.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Edge edge = (Edge) obj;
        return nameEdge.equals(edge.nameEdge) && target.equals(edge.target);
    }
}
