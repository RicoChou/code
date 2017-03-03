package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rico on 3/2/2017.
 */
public class Vertex<T> {
    /* 顶点标识 */
    public T label;
    /* 相邻的顶点 */
    public List<Edge> adjacentList;
    /* 是否已访问过 */
    public boolean visited;
    /* 是否已知路径为最短 */
    public boolean known;
    /* 入度 */
    public int indegree;
    /* 单源路径长 */
    public int dist;

    public Vertex(T label) {
        this.label = label;
        this.visited = false;
        this.adjacentList = new LinkedList<>();
        this.indegree = 0;
        this.dist = Integer.MAX_VALUE;
        this.known = false;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

}
