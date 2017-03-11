package graph;

import java.util.*;

/**
 * Created by Rico on 3/2/2017.
 */
public class GraphTest {
    public static void main(String[] args) {
        int[][] UnweightedDirectedGraph = {{1,2,1},{1,4,1},{1,3,1},{2,4,1},{2,5,1},{3,6,1},{4,6,1},
                {4,7,1},{4,3,1},{5,4,1},{5,7,1},{7,6,1}};

        int[][] UnweightedDirectedGraphForDist = {{1,2,1},{1,4,1},{3,1,1},{2,4,1},{2,5,1},{3,6,1},{4,6,1},
                {4,7,1},{4,3,1},{4,5,1},{5,7,1},{7,6,1}};

        int[][] WeightedDirectedGraphForDist = {{1,2,2},{1,4,1},{3,1,4},{2,4,3},{2,5,10},{3,6,5},{4,6,8},
                {4,7,4},{4,3,2},{4,5,2},{5,7,6},{7,6,1}};

        int[][] WeightedUndirectedGraph = {{1,2,2}, {1,4,1}, {1,3,4}, {2,1,2}, {2,4,3}, {2,5,10}, {3,1,4},
                {3,4,2}, {3,6,5}, {4,1,1}, {4,2,3}, {4,5,7}, {4,7,4}, {4,6,8}, {4,3,2}, {5,2,10}, {5,4,7},
                {5,7,6}, {6,3,5}, {6,4,8}, {6,7,1}, {7,6,1}, {7,4,4}, {7,5,6}};
        
        Map<Integer, Vertex> graph = createGraph(WeightedUndirectedGraph);
//        printGraph(graph);

//        topologicalSort(graph);
//        topologicalSort2(createGraph(UnweightedDirectedGraph));

//        System.out.println(Arrays.toString(Dijkstra(graph, graph.get(1))));
//        [0, 2, 3, 1, 3, 6, 5]

//        System.out.println(Arrays.toString(Prim(graph)));
//        [[4, 1, 1], [2, 1, 2],[3, 4, 2], [7, 4, 4], [5, 7, 6], [6, 7, 1]]

        Kruskal(graph);
    }

    /**
     * 根据边构建图，边定义[起点，终点，权值]
     * @param edges
     * @return
     */
    public static Map<Integer, Vertex> createGraph(int[][] edges){
        Map<Integer, Vertex> graph = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            graph.put(i, new Vertex(i));
        }

        for (int i = 0; i < edges.length; i++) {
            Edge edge = new Edge(graph.get(edges[i][0]), graph.get(edges[i][1]),edges[i][2]);
            graph.get(edges[i][0]).adjacentList.add(edge);
            graph.get(edges[i][1]).indegree++;
        }

        return graph;
    }

    /**
     * 带入度（indegree）打印图
     * @param graph
     */
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

    /**
     * 拓扑排序方法一  O(|V|^2)
     * @param graph
     */
    public static void topologicalSort(Map<Integer, Vertex> graph){

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

    /**
     * 拓扑排序方法二 使用队列存放入度为0的顶点  O(|V| + |E|)
     * @param graph
     */
    public static void topologicalSort2(Map<Integer, Vertex> graph){

        for (int i = 1; i <= 7; i++) {
            Vertex v = findVertexOfIndegreeZeroWithQueue(graph);
            if (v == null) {
                System.out.println("graph has a cycle!");
                break;
            }
            System.out.print(v.label + "-->");
        }

    }

    public static Vertex findVertexOfIndegreeZero(Map<Integer, Vertex> graph){
        Vertex v = graph.get(graph.keySet().stream().filter(key -> graph.get(key).indegree == 0 && graph.get(key).visited == false).findFirst().get());
        if (v != null) {
            v.visited = true;
        }
        return v;
    }

    static Queue<Vertex> indegreeZero = new LinkedList<>();
    public static Vertex findVertexOfIndegreeZeroWithQueue(Map<Integer, Vertex> graph){
        if (indegreeZero.size() == 0) {
            indegreeZero.offer(findVertexOfIndegreeZero(graph));
        }
        Vertex e = indegreeZero.poll();
        List<Edge> list = e.adjacentList;
        for (Edge edge : list) {
            if (--edge.end.indegree == 0) {
                indegreeZero.offer(edge.end);
            }
        }
        return e;
    }

    /**
     * 单源最短路径问题 解法一 O(|V|^2)
     * @param graph   图
     * @param vertex  顶点
     * @return        顶点到所有点的路径长度
     */
    private static int[] shortestDist(Map<Integer, Vertex> graph, Vertex vertex) {
        int[] res = new int[graph.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = Integer.MAX_VALUE;
        res[(int) vertex.label - 1] = 0;

        for (int currDist = 0; currDist < res.length; currDist++) {
            /* 遍历所有顶点找到当前距离为currDist，操作需要O(|V|)比较费时 */
            for (int i = 1; i <= graph.size(); i++) {
                Vertex v = graph.get(i);
                if (v.visited == false && currDist == res[i - 1]) {
                    v.visited = true;
                    List<Edge> list = v.adjacentList;
                    for (Edge edge : list)
                        if (res[((int) edge.end.label) - 1] == Integer.MAX_VALUE)
                            res[((int) edge.end.label) - 1] = currDist + 1;
                }
            }
        }

        return res;
    }

    /**
     * 单源最短路径问题 解法二 O(|E|+|V|), 使用队列，优化遍历所有顶点找到当前距离为currDist，时间复杂度整体为
     * @param graph   图
     * @param vertex  顶点
     * @return        顶点到所有点的路径长度
     */
    private static int[] shortestDist2(Map<Integer, Vertex> graph, Vertex vertex) {
        Queue<Vertex> queue = new LinkedList<>();
        int[] res = new int[graph.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = Integer.MAX_VALUE;
        res[(int) vertex.label - 1] = 0;
        queue.offer(graph.get(vertex.label));

        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            List<Edge> list = v.adjacentList;
            for (Edge edge : list) {
                if (res[((int) edge.end.label) - 1] == Integer.MAX_VALUE) {
                    res[((int) edge.end.label) - 1] = res[((int) v.label) - 1] + 1;
                    queue.offer(edge.end);
                }
            }
        }
        return res;
    }

    /**
     * 单源最短路径问题（赋权图）Dijkstra算法（greedy algorithm）
     * @param graph
     * @param vertex
     * @return
     */
    private static int[] Dijkstra(Map<Integer, Vertex> graph, Vertex vertex) {
        int[] res = new int[graph.size()];
        PriorityQueue<Vertex> queue = new PriorityQueue<>((v1, v2) -> (Integer)v1.dist - (Integer)v2.dist);
        graph.get(vertex.label).dist = 0;

        Vertex v = graph.get(vertex.label);
        while (v != null) {
            v.known = true;
            List<Edge> list = v.adjacentList;
            for (Edge edge : list) {
                if (!edge.end.known && v.dist + edge.value < edge.end.dist) {
                    edge.end.dist = v.dist + edge.value;
                }
                if (!edge.end.visited) {
                    edge.end.visited = true;
                    queue.offer(edge.end);
                }
            }
            v = queue.poll();
        }

        for (int i = 0; i < graph.size(); i++)
            res[i] = graph.get(i + 1).dist;
        return res;
    }

    /**
     * 赋权无向图的最小生成树Prim算法
     * @param graph
     * @return 边的数组
     */
    public static Edge[] Prim(Map<Integer, Vertex> graph){
        Edge[] edges = new Edge[graph.size() - 1];
        PriorityQueue<Vertex> queue = new PriorityQueue<>((v1, v2) -> (Integer)v1.dist - (Integer)v2.dist);
        queue.offer(graph.get(1));
        graph.get(1).visited = true;

        int j = 0;
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            List<Edge> list = v.adjacentList;
            Edge temp = new Edge(null, null, Integer.MAX_VALUE);
            for (Edge edge : list) {
                if (edge.end.known && edge.value < temp.value) {
                    temp = edge;
                }
                if (!edge.end.visited) {
                    edge.end.visited = true;
                    //排序方式为初始顶点（1）到当前顶点总路径值
                    edge.end.dist = edge.start.dist + edge.value;
                    queue.offer(edge.end);
                }
            }
            if (temp.value != Integer.MAX_VALUE) {
                edges[j++] = temp;
            }
            v.known = true;
        }

        return edges;
    }

    /**
     * 赋权无向图的最小生成树Kruskal算法
     * @param graph
     * @return 边的数组
     */
    public static Edge[] Kruskal(Map<Integer, Vertex> graph){
        Edge[] edges = new Edge[graph.size() - 1];
        Set<Vertex> vertexSet = new HashSet<>();


        LinkedList<Edge> list = new LinkedList<>();
        graph.keySet().forEach(key -> list.addAll(graph.get(key).adjacentList));
        TreeSet<Edge> edgeTreeSet = new TreeSet<>(list);
//        edgeTreeSet.stream().forEach(System.out::println);

        int edgesAccepted = 0;

        for (Edge edge : edgeTreeSet) {
            if (!isCircle(edge))
                edges[edgesAccepted++] = edge;
            if (edgesAccepted == graph.size() - 1)
                break;
        }
        return edges;
    }
}
