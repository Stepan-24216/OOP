package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestObjects {
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
        Edge a = new Edge("a", y);
        assertEquals(1, x.getEdges().size());
        x.addEdge("b", z);
        assertEquals(2, x.getEdges().size());
        for (Edge edge : x.getEdges()) {
            assertEquals(edge.nameEdge, edge.getNameEdge());
        }
        x.deleteEdge(a);
        assertEquals(1, x.getEdges().size());
        assertEquals("b", x.getEdges().get(0).getNameEdge());
        assertTrue(a.equals(a));
    }

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
