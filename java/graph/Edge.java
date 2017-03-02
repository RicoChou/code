package graph;

/**
 * Created by Rico on 3/2/2017.
 */
public class Edge {
    /* 边的起点 */
    public Vertex start;
    /* 边的终点 */
    public Vertex end;
    /* 边的权值 */
    public int value;

    public Edge(Vertex start, Vertex end, int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }
}
