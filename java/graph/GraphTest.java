package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 3/2/2017.
 */
public class GraphTest {
    public static void main(String[] args) {
        Map<Integer, Vertex> graph = createGraph();
        printGraph(graph);

        for (int i = 1; i <= 7; i++) {
            Vertex v = findVertexOfIndegreeZero(graph);
            if (v == null) {
                System.out.println("graph has a cycle!");
                break;
            }
            System.out.print(v.label + "-->");
            List<Edge> list = graph.get(v.label).adjacentList;
            for (Edge e : list) {
                e.end.indegree--;
            }
        }
    }

    public static Map<Integer, Vertex> createGraph(){
        Map<Integer, Vertex> graph = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            graph.put(i, new Vertex(i));
        }

        int[][] edges = {{1,2,1},{1,4,1},{1,3,1},{2,4,1},{2,5,1},{3,6,1},{4,6,1},
                {4,7,1},{4,3,1},{5,4,1},{5,7,1},{7,6,1}};

        for (int i = 0; i < edges.length; i++) {
            Edge edge = new Edge(graph.get(edges[i][0]), graph.get(edges[i][1]),edges[i][2]);
            graph.get(edges[i][0]).adjacentList.add(edge);
            graph.get(edges[i][1]).indegree++;
        }

        return graph;
    }

    public static void printGraph(Map<Integer, Vertex> graph){
        for (int i = 1; i <= 7; i++) {
            System.out.print(graph.get(i).indegree + "      ");
            System.out.print(graph.get(i).label);
            List<Edge> list = graph.get(i).adjacentList;
            for (Edge edge : list)
                System.out.print("-->" + edge.end.label);
            System.out.println("-->||");
        }
    }

    public static Vertex findVertexOfIndegreeZero(Map<Integer, Vertex> graph){
        return graph.get(graph.keySet().stream().filter(key -> graph.get(key).indegree == 0).findFirst().get());
    }
}
