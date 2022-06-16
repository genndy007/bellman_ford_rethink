package com.github.genndy007.bellman_ford;

public class Edge {
    public Vertex startVertex;
    public Vertex endVertex;
    public int weight;

    public Edge(Vertex startVertex, Vertex endVertex, int weight) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
    }
}
