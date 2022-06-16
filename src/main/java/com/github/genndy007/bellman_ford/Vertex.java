package com.github.genndy007.bellman_ford;

public class Vertex {
    public final int id;
    public int distance;

    public Vertex(int id) {
        this.id = id;
    }

    public String toString() {
        return "V_id=" + String.valueOf(id);
    }
}
