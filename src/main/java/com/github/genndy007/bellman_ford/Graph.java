package com.github.genndy007.bellman_ford;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public List<Vertex> vertices;
    public List<Edge> edges;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public String toString() {
        String response = "Graph with\n vertices_ids = ";
        for (Vertex v : vertices) {
            response += String.valueOf(v.id) + " ";
        }
        response += "\n edges = ";
        for (Edge e : edges) {
            response += "(" +
                    String.valueOf(e.startVertex.id) + ", " +
                    String.valueOf(e.endVertex.id) + ", " +
                    String.valueOf(e.weight) + ") ";
        }
        return response;
    }
}
