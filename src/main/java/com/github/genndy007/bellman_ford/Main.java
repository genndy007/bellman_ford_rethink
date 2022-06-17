package com.github.genndy007.bellman_ford;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String graphFilePath = "graph_little.txt";
        GraphReader gr = new GraphReader(graphFilePath);
        Graph g = gr.readAsEdgeList();

        BellmanFord bf = new BellmanFord(g, 0);
        bf.sequentialAlgorithm();

        g.printDistances();



    }
}
