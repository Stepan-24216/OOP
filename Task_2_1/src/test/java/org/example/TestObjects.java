package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.example.objects.Color;
import org.example.objects.Edge;
import org.example.objects.Vertex;

/**
 * Тесты для составляющих граф объектов.
 */
public class TestObjects {
    /**
     * Тесты для вершин.
     */
    @Test
    void createVertex() {
        Vertex x = new Vertex("1");
        assertEquals("1", x.getName());
        assertEquals(Color.WHITE, x.getColor());
        Vertex y = new Vertex("2");
        assertEquals("2", y.getName());
        Vertex z = new Vertex("3");
        assertEquals("3", z.getName());
        Vertex v = new Vertex("5");
        assertEquals("5", v.getName());

        x.setColor(Color.GRAY);
        assertEquals(Color.GRAY, x.getColor());
        x.setColor(Color.BLACK);
        assertEquals(Color.BLACK, x.getColor());
        x.addEdge("a", y);
        x.addEdge("b", z);
        Edge a = new Edge("a", y);
        x.deleteEdge(a);
        assertEquals(1, x.getEdges().size());
        assertEquals("b", x.getEdges().get(0).getNameEdge());
        assertTrue(a.equals(a));
    }

    /**
     * Тесты для рёбер.
     */
    @Test
    void createEdge() {
        Vertex x = new Vertex("1");
        Vertex y = new Vertex("2");
        Edge a = new Edge("a", y);
        assertEquals("a", a.getNameEdge());
        assertEquals(y, a.getTarget());
        Edge b = new Edge("b", x);
        assertEquals("b", b.getNameEdge());
        assertEquals(x, b.getTarget());
        assertTrue(a.equals(a));
    }
}
