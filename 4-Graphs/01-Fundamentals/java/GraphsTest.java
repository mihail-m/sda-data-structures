package graphs;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.function.BiFunction;

public class GraphsTest {
    @Test
    public void testNumberOfPathsOfFixedLength() {
        NumberOfPathsOfFixedLength.PathsIndex index = NumberOfPathsOfFixedLength.createIndex(buildGraph(), 4);
        Assert.assertEquals(16, index.paths(2, 4));
    }

    @Test
    public void testDijkstra() {
        testShortestPaths(Dijkstra::dijkstraSparse);
        testShortestPaths(Dijkstra::dijkstraDense);
    }

    @Test
    public void testBellmanFord() {
        testShortestPaths(BellmanFord::shortestPaths);
    }

    @Test
    public void testFloyd() {
        int [][] expectedResult = {
                {0, 4, 12, 19, 21, 11, 9, 8, 14},
                {4, 0, 8, 15, 22, 12, 12, 11, 10},
                {12, 8, 0, 7, 14, 4, 6, 7, 2},
                {19, 15, 7, 0, 9, 11, 13, 14, 9},
                {21, 22, 14, 9, 0, 10, 12, 13, 16},
                {11, 12, 4, 11, 10, 0, 2, 3, 6},
                {9, 12, 6, 13, 12, 2, 0, 1, 6},
                {8, 11, 7, 14, 13, 3, 1, 0, 7},
                {14, 10, 2, 9, 16, 6, 6, 7, 0}
        };
        Assert.assertArrayEquals(expectedResult, Floyd.floyd(buildGraph()));
    }

    @Test
    public void testPrim() {
        testMinimumSpanningTree(Prim.minimumSpanningTree(buildGraph()));
    }

    @Test
    public void testKruskal() {
        testMinimumSpanningTree(Kruskal.minimumSpanningTree(buildGraph()));
    }

    @Test
    public void testCycles() {
        Assert.assertTrue(Cycles.findCycle(buildGraph()));
        Assert.assertFalse(Cycles.findCycle(buildDirectedGraph()));
    }

    public Graph buildGraph() {
        Graph graph = new Graph(9);
        addEdges(graph);
        return graph;
    }

    public DirectedGraph buildDirectedGraph() {
        DirectedGraph graph = new DirectedGraph(9);
        addEdges(graph);
        return graph;
    }

    public void addEdges(Graph graph) {
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 7, 8);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 7, 11);
        graph.addEdge(2, 3, 7);
        graph.addEdge(2, 5, 4);
        graph.addEdge(2, 8, 2);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 14);
        graph.addEdge(4, 5, 10);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 7, 1);
        graph.addEdge(6, 8, 6);
        graph.addEdge(7, 8, 7);
    }

    public void testShortestPaths(BiFunction<Graph, Integer, int[]> function) {
        int[] expectedResult = {0, 4, 12, 19, 21, 11, 9, 8, 14};
        Assert.assertArrayEquals(expectedResult, function.apply(buildGraph(), 0));
    }

    public void testMinimumSpanningTree(Graph tree) {
        Assert.assertEquals(9, Objects.requireNonNull(tree).vertices);

        DFS.dfs(tree, 0);
        for (int vertex = 0; vertex < tree.vertices; vertex++) {
            Assert.assertTrue(tree.visited[vertex]);
        }

        int sum = 0;
        for (int i = 0; i < tree.edgesList.size(); i += 2) {
            sum += tree.edgesList.get(i).cost;
        }
        Assert.assertEquals(37, sum);
    }
}
