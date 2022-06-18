package com.github.genndy007.bellman_ford;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BellmanFord {
    public Graph graph;
    public int startId = 0;

    public BellmanFord(Graph graph, int startId) {
        this.graph = graph;
        this.startId = startId;
    }

    public void sequentialAlgorithm() {
        for (Vertex v : graph.vertices) {
            v.distance = Integer.MAX_VALUE - 100;
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

    public void parallelAlgorithm(int numberOfThreads) {
        MyThreadPool threadPool = new MyThreadPool(numberOfThreads, 1000);

        for (Vertex v : graph.vertices) {
            threadPool.execute(() -> {
                v.distance = Integer.MAX_VALUE - 100;
            });
        }
        threadPool.waitUntilAllTasksFinished();
        graph.vertices.get(startId).distance = 0;


        // prepare chunks containers
        List<ConcurrentLinkedQueue<Edge>> adjEdges = new ArrayList<>();
        int chunkSize = graph.vertices.size() / numberOfThreads;
        List<IntPair> chunks = new ArrayList<>();
        for (int i = 0; i * chunkSize < graph.vertices.size(); i++) {
            chunks.add(new IntPair(i * chunkSize, i * chunkSize + chunkSize));
            adjEdges.add(new ConcurrentLinkedQueue<>());
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


        for (int i = 1; i < graph.vertices.size(); i++) {
            for (ConcurrentLinkedQueue<Edge> queue : adjEdges) {
                threadPool.execute(() -> {
                    for (Edge e : queue) {
                        graph.vertices.get(e.endId).distance = Math.min(
                                graph.vertices.get(e.endId).distance,
                                graph.vertices.get(e.startId).distance + e.weight
                        );
                    }
                });
            }
            threadPool.waitUntilAllTasksFinished();
            for (Edge e : seqEdges) {
                graph.vertices.get(e.endId).distance = Math.min(
                        graph.vertices.get(e.endId).distance,
                        graph.vertices.get(e.startId).distance + e.weight
                );
            }
        }
        threadPool.stop();



    }

}
