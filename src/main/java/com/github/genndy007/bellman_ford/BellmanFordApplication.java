package com.github.genndy007.bellman_ford;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BellmanFordApplication {
    public static void main(String[] args) throws IOException {
        String graphFilePath = "graphs/graph_little.txt";
        GraphReader gr = new GraphReader(graphFilePath);
        Graph g = gr.readAsEdgeList();

        System.out.println(g);

    }
}
