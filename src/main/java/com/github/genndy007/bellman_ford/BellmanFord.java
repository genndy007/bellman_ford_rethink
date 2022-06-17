package com.github.genndy007.bellman_ford;

public class BellmanFord {
    public Graph graph;
    public int startId = 0;

    public BellmanFord(Graph graph, int startId) {
        this.graph = graph;
        this.startId = startId;
    }

    public void sequentialAlgorithm() {
        for (Vertex v: graph.vertices) {
            v.distance = Integer.MAX_VALUE;
        }
        graph.vertices.get(startId).distance = 0;

        for (int i = 1; i < graph.vertices.size(); i++) {
            for (Edge e: graph.edges) {
                graph.vertices.get(e.endId).distance = Math.min(
                        graph.vertices.get(e.endId).distance,
                        graph.vertices.get(e.startId).distance + e.weight
                );
            }
        }
    }
}
