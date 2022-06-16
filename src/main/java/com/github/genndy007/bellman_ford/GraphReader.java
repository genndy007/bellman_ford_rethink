package com.github.genndy007.bellman_ford;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {
    private final String filePath;

    public GraphReader(String filePath) {
        this.filePath = filePath;
    }

    private BufferedReader getBufferedReader() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filePath);
        if (is == null) throw new IllegalArgumentException(filePath + " not found in resources dir");
        return new BufferedReader(new InputStreamReader(is));
    }

    public Graph readAsEdgeList() throws IOException {
        BufferedReader br = getBufferedReader();
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                String[] splited = line.split("\\s+");
                int verticesNum = Integer.parseInt(splited[0]);
                for (int id = 0; id < verticesNum; id++) {
                    vertices.add(new Vertex(id));
                }
                firstLine = false;
                continue;
            }

            String[] splited = line.split("\\s+");
            int startId = Integer.parseInt(splited[0]);
            int endId = Integer.parseInt(splited[1]);
            int weight = Integer.parseInt(splited[2]);

            edges.add(new Edge(vertices.get(startId), vertices.get(endId), weight));

        }

        return new Graph(vertices, edges);
    }


}
