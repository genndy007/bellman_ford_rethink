package com.github.genndy007.bellman_ford;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class AlgoRunner {
    private final Integer TIME_TESTS = 5;
    private List<String> fileNames;

    public List<String> getFileNames() {
        return fileNames;
    }

    public void runBothCompareResults() throws IOException, InterruptedException {
        for (String fileName: fileNames) {
            GraphReader gr = new GraphReader(fileName);
            Graph g = gr.readAsEdgeList();
            BellmanFord bf = new BellmanFord(g, 0);

            bf.sequentialAlgorithm();
            List<Integer> seqDistances = collectVertices(g);

            bf.parallelAlgorithm(4);
            List<Integer> parDistances = collectVertices(g);

            boolean passed = seqDistances.equals(parDistances);
            System.out.println(g.info() + " " + passed);

        }
    }

    public void runCheckTime(Integer size, Integer density, boolean isParallel, Integer numOfThreads) throws IOException, InterruptedException {
        String fileName = "graph_v" + size + "_p0" + density + ".txt";
        GraphReader gr = new GraphReader(fileName);
        Graph g = gr.readAsEdgeList();
        BellmanFord bf = new BellmanFord(g, 0);

        List<Integer> execTimes = new ArrayList<>(TIME_TESTS);
        for (int i = 0; i < TIME_TESTS; i++) {
            System.out.println("Test " + i);
            long startTime = System.currentTimeMillis();
            if (isParallel) {
                bf.parallelAlgorithm(numOfThreads);
            } else {
                bf.sequentialAlgorithm();
            }
            long endTime = System.currentTimeMillis();
            int execTime = (int) (endTime - startTime);
            execTimes.add(execTime);
        }
        System.out.println(execTimes);
        double average = execTimes
                .stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0.0);
        System.out.println("Average time (ms): " + average);
    }

    private void generateFileNames(List<Integer> graphSizes, List<Integer> densities) {
        this.fileNames = new ArrayList<>(graphSizes.size() * densities.size());
        for (Integer size: graphSizes) {
            for (Integer density : densities) {
                String fileName = "graph_v" + size + "_p0" + density + ".txt";
                fileNames.add(fileName);
            }
        }
    }

    private List<Integer> collectVertices(Graph g) {
        List<Integer> distances = new ArrayList<>(g.vertices.size());
        for (Vertex v: g.vertices) {
            distances.add(v.distance);
        }
        return distances;
    }
}
