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
    /* 入度 */
    public int indegree;

    public Vertex(T label) {
        this.label = label;
        this.visited = false;
        adjacentList = new LinkedList<>();
        indegree = 0;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

}
