package factory;

import java.util.ArrayDeque;
import java.util.Deque;

public class EngineStorage {
    Deque<Engine> storage;
    Integer engineCount = 0;
    Integer currentCount = 0;
    Integer maxCount = 50;

    /*EngineStorage() {
        storage = new ArrayDeque<>();
    }*/

    public EngineStorage(Integer maxCount) {
        storage = new ArrayDeque<>();
        this.maxCount = maxCount;
    }

    public void addEngine(Engine engine) {
        synchronized (this) {
            try {
                while (isFilled()) {
                    wait();
                }
            }
            catch (InterruptedException err) {
                err.printStackTrace();
            }
            storage.add(engine);
            currentCount++;
            engineCount++;
            notifyAll();
        }
    }

    public Integer getEngineCount() {
        synchronized (this) {
            return currentCount;
        }
    }

    public Engine getEngine() {
        synchronized (this) {
            try {
                while (currentCount == 0) {
                    wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            Engine engine = storage.pollLast();
            currentCount--;
            notifyAll();
            return engine;
        }
    }
    public boolean isFilled() {
        return (maxCount.equals(currentCount));
    }
}
