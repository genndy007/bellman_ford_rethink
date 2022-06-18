package com.github.genndy007.bellman_ford;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String graphFilePath = "graph_v1000.txt";
        GraphReader gr = new GraphReader(graphFilePath);
        Graph g = gr.readAsEdgeList();

        BellmanFord bf = new BellmanFord(g, 0);

        long startTime = System.currentTimeMillis();
//        bf.parallelAlgorithm(16);
//        bf.sequentialAlgorithmWithCheck();
        bf.parallelAlgorithmExecutorService(8);
        long endTime = System.currentTimeMillis();
        g.printDistances();

        System.out.println("That took " + (endTime - startTime) + " milliseconds");




    }
}
