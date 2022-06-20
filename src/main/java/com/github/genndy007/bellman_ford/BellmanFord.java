package com.github.genndy007.bellman_ford;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BellmanFord {
    public Graph graph;
    public int startId = 0;
    private int MAX_EDGE_WEIGHT = 100;

    public BellmanFord(Graph graph, int startId) {
        this.graph = graph;
        this.startId = startId;
    }

    public void sequentialAlgorithm() {
        for (Vertex v : graph.vertices) {
            v.distance = Integer.MAX_VALUE - MAX_EDGE_WEIGHT;
        }
        graph.vertices.get(startId).distance = 0;

        for (int i = 1; i < graph.vertices.size(); i++) {
            for (Edge e : graph.edges) {
                graph.vertices.get(e.endId).distance = Math.min(
                        graph.vertices.get(e.endId).distance,
                        graph.vertices.get(e.startId).distance + e.weight
                );
            }
        }
    }


    public void parallelAlgorithm(int numberOfThreads) throws InterruptedException {
        for (Vertex v : graph.vertices) {
            v.distance = Integer.MAX_VALUE - 100;
        }
        graph.vertices.get(startId).distance = 0;

        // prepare chunks containers
        List<List<Edge>> adjEdges = new ArrayList<>();
        int chunkSize = graph.vertices.size() / numberOfThreads;
        List<IntPair> chunks = new ArrayList<>();
        for (int i = 0; i * chunkSize < graph.vertices.size(); i++) {
            chunks.add(new IntPair(i * chunkSize, i * chunkSize + chunkSize));
            adjEdges.add(new ArrayList<>());
        }

        // split edges into parallelizable and seq
        List<Edge> seqEdges = new ArrayList<>(graph.edges);
        for (Edge e : graph.edges) {
            for (int i = 0; i < chunks.size(); i++) {
                IntPair pair = chunks.get(i);
                if (e.startId >= pair.x && e.startId < pair.y && e.endId >= pair.x && e.endId < pair.y) {
                    adjEdges.get(i).add(e);
                    seqEdges.remove(e);
                    break;
                }
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Callable<String>> tasks = new ArrayList<>();
        AtomicBoolean distUpdated = new AtomicBoolean(false);
        for (int i = 1; i < graph.vertices.size(); i++) {
            distUpdated.set(false);
            tasks.clear();
            for (List<Edge> queue : adjEdges) {
                tasks.add(() -> {
                    for (Edge e : queue) {
                        int distBefore = graph.vertices.get(e.endId).distance;
                        graph.vertices.get(e.endId).distance = Math.min(
                                graph.vertices.get(e.endId).distance,
                                graph.vertices.get(e.startId).distance + e.weight
                        );

                        if (distBefore != graph.vertices.get(e.endId).distance && !distUpdated.get()) {
                            distUpdated.set(true);
                        }
                    }
                    return "null";
                });
            }
            executorService.invokeAll(tasks);

            for (Edge e : seqEdges) {
                int distBefore = graph.vertices.get(e.endId).distance;
                graph.vertices.get(e.endId).distance = Math.min(
                        graph.vertices.get(e.endId).distance,
                        graph.vertices.get(e.startId).distance + e.weight
                );
                if (distBefore != graph.vertices.get(e.endId).distance && !distUpdated.get()) {
                    distUpdated.set(true);
                }
            }

            if (!distUpdated.get()) {
                break;
            }
        }
        executorService.shutdown();

    }
}
