package threadpool;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    int threadsCount;
    PoolWorker[] pool;
    LinkedBlockingQueue queue;

    public ThreadPool(int count) {
        threadsCount = count;
        queue = new LinkedBlockingQueue();
        pool = new PoolWorker[count];
        for (int i = 0; i < count; ++i) {
            pool[i] = new PoolWorker();
            pool[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
        //return task;
    }
    public int getQueueSize() {
        return queue.size();
    }
    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task = (Runnable) queue.poll();
                }
                try {
                    task.run();
                    synchronized (task) {
                        task.notifyAll(); //added
                    }
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }
}

