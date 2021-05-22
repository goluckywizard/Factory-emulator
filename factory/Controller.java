package factory;

import threadpool.ThreadPool;

import java.util.Comparator;
import java.util.Deque;
import java.util.concurrent.PriorityBlockingQueue;

public class Controller implements Runnable{
    //PriorityBlockingQueue<Worker> workers;
    Worker[] workers;
    CarStorage carStorage;
    ThreadPool threadPool;

    public Controller(Worker[] workers, CarStorage carStorage, ThreadPool threadPool) {
        this.workers = workers;
        this.carStorage = carStorage;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (carStorage) {
                if (carStorage.currentCount.equals(carStorage.maxCount)) {
                    try {
                        carStorage.wait();
                    }
                    catch (InterruptedException err) {
                        err.printStackTrace();
                }
            }

            Worker worker = null;
            for (Worker iter : workers) {
                synchronized (iter) {
                    if (!iter.isOnWork) {
                        worker = iter;
                        break;
                    }
                }
            }
                //worker = workers.poll();

            if (worker != null) {
                worker.isOnWork = true;
                threadPool.execute(worker);
            }
                //workers.add(worker);
            }
            //workers.add(return_worker);
        }
    }
}
