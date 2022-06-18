package com.github.genndy007.bellman_ford;

public class IntPair {
    final int x;
    final int y;

    IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
