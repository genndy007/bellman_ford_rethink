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
                    String.valueOf(e.startId) + ", " +
                    String.valueOf(e.endId) + ", " +
                    String.valueOf(e.weight) + ") ";
        }
        return response;
    }

    public void printDistances() {
        String line;
        for (Vertex v: vertices) {
            line = String.valueOf(v.id) + " " + String.valueOf(v.distance);
            System.out.println(line);
        }
    }
}
