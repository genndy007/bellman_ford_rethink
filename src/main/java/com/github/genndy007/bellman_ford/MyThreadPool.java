package com.github.genndy007.bellman_ford;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {
    private BlockingQueue<Runnable> taskQueue = null;
    private List<MyPoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public MyThreadPool(int numberOfThreads, int maxNumberOfTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxNumberOfTasks);

        for (int i = 0; i < numberOfThreads; i++) {
            MyPoolThreadRunnable poolThreadRunnable = new MyPoolThreadRunnable(taskQueue);
            runnables.add(new MyPoolThreadRunnable(taskQueue));
        }

        for (MyPoolThreadRunnable runnable: runnables) {
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw new IllegalStateException("ThreadPool is stopped, not working");
        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (MyPoolThreadRunnable runnable: runnables) {
            runnable.doStop();   // stop all threads
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (this.taskQueue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
