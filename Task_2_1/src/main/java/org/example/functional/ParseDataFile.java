package org.example.functional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.graphstoragemethods.Graph;
import org.example.objects.Vertex;

/**
 * Разбор строки из файла.
 */
public class ParseDataFile {
    /**
     * Разбор строки из файла.
     */
    public static void parseData(String input, Graph list, boolean needNameEdge) {
        Pattern pattern1 = Pattern.compile("^(\\S+) (\\S+)$");

        Pattern pattern2 = Pattern.compile("^(\\S+) (\\S+) (\\S+)$");

        Matcher matcher1 = pattern1.matcher(input);
        Matcher matcher2 = pattern2.matcher(input);

        if (matcher1.matches() && !needNameEdge) {
            vertexAndVertex(matcher1.group(1), matcher1.group(2), list);
        } else if (matcher2.matches()) {
            vertexAndVertexAndEdge(matcher2.group(1), matcher2.group(2), matcher2.group(3), list);
        } else {
            System.out.println("ОШИБКА: Неверный формат данных!");
        }
    }

    /**
     * Ситуация вeршина + вершина.
     */
    public static void vertexAndVertex(String vertex1, String vertex2, Graph list) {
        Vertex a;
        Vertex b;
        if (!list.getVertexList().contains(new Vertex(vertex1))) {
            a = new Vertex(vertex1);
            list.addVertex(a);
        } else {
            a = list.getVertexList().get(list.getVertexList().indexOf(new Vertex(vertex1)));
        }
        if (!list.getVertexList().contains(new Vertex(vertex2))) {
            b = new Vertex(vertex2);
            list.addVertex(b);
        } else {
            b = list.getVertexList().get(list.getVertexList().indexOf(new Vertex(vertex2)));
        }
        list.addEdge(a.getName() + "-" + b.getName(), a, b);
    }

    /**
     * Ситуация виршина+вершина+ребро.
     */
    public static void vertexAndVertexAndEdge(String vertex1, String vertex2,
                                              String edgeName, Graph list) {
        Vertex a;
        Vertex b;
        if (!list.getVertexList().contains(new Vertex(vertex1))) {
            a = new Vertex(vertex1);
            list.addVertex(a);
        } else {
            a = list.getVertexList().get(list.getVertexList().indexOf(new Vertex(vertex1)));
        }
        if (!list.getVertexList().contains(new Vertex(vertex2))) {
            b = new Vertex(vertex2);
            list.addVertex(b);
        } else {
            b = list.getVertexList().get(list.getVertexList().indexOf(new Vertex(vertex2)));
        }
        list.addEdge(edgeName, a, b);
    }
}
