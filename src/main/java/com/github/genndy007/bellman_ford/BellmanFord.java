package com.github.genndy007.bellman_ford;

public class BellmanFord {
    public Graph graph;
    public int startId = 0;

    public BellmanFord(Graph graph, int startId) {
        this.graph = graph;
        this.startId = startId;
    }

    public void sequentialAlgorithm() {
        graph.vertices.get(startId).distance = 0;
        System.out.println(graph.vertices.get(startId).distance);
    }
}
