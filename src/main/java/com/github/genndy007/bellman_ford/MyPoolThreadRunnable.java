package com.github.genndy007.bellman_ford;

import java.util.concurrent.BlockingQueue;

public class MyPoolThreadRunnable implements Runnable {
    private Thread thread = null;
    private BlockingQueue<Runnable> taskQueue = null;
    private boolean isStopped = false;

    public MyPoolThreadRunnable(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.thread.interrupt();  // stop this thread
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
