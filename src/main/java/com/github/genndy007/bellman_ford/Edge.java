package com.github.genndy007.bellman_ford;

public class Edge {
    public int startId;
    public int endId;
    public int weight;

    public Edge(int startId, int endId, int weight) {
        this.startId = startId;
        this.endId = endId;
        this.weight = weight;
    }

    public String toString() {
        return "Edge startId=" + String.valueOf(startId) +
                " endId=" + String.valueOf(endId) +
                " weight=" + String.valueOf(weight);
    }
}
