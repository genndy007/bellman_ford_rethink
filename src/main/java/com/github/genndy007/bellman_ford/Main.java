package com.github.genndy007.bellman_ford;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AlgoRunner runner = new AlgoRunner();
        runner.runCheckTime(2500, 1, true, 16);
    }
}
