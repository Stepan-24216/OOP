package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseDataFile {
    public static void parseData(String input, Graph list, boolean needNameEdge) {
        Pattern pattern1 = Pattern.compile("^(\\S+) (\\S+)$");

        Pattern pattern2 = Pattern.compile("^(\\S+) (\\S+) (\\S+)$");

        Matcher matcher1 = pattern1.matcher(input);
        Matcher matcher2 = pattern2.matcher(input);

        if (matcher1.matches() && !needNameEdge) {
            VertexAndVertex(matcher1.group(1), matcher1.group(2), list);
        } else if (matcher2.matches()) {
            VertexAndVertexAndEdge(matcher2.group(1), matcher2.group(2), matcher2.group(3), list);
        } else {
            System.out.println("ОШИБКА: Неверный формат данных!");
        }
    }

    public static void VertexAndVertex(String vertex1, String vertex2, Graph list) {
        Vertex a, b;
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

    public static void VertexAndVertexAndEdge(String vertex1, String vertex2, String edgeName,
                                              Graph list) {
        Vertex a, b;
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
