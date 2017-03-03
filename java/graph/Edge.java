package graph;

/**
 * Created by Rico on 3/2/2017.
 */
public class Edge implements Comparable{
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

    @Override
    public int hashCode() {
        if (start == null || end == null)
            return 0;
        int a = start.hashCode();
        int b = end.hashCode();
        if (a < b) {
            a = a + b;
            b = a - b;
            a = a - b;
        }

        return (a<<5) + b;
    }

    @Override
    public String toString() {
        return "[" + start.label + ", " + end.label + ", " + value + "]";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Edge) {
            Edge other = (Edge) o;
            return value - other.value;
        }
        return 0;
    }
}
