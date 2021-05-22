package factory;

import java.util.ArrayDeque;
import java.util.Deque;

public class CarcaseStorage {
    Deque<Carcase> storage;
    Integer carcaseCount = 0;
    Integer currentCount = 0;
    Integer maxCount;

    /*CarcaseStorage() {
        storage = new ArrayDeque<>();
    }*/

    public CarcaseStorage(Integer maxCount) {
        storage = new ArrayDeque<>();
        this.maxCount = maxCount;
    }

    public void addCarcase(Carcase carcase) {
        synchronized (this) {
            try {
                while (isFilled()) {
                    wait();
                }
            }
            catch (InterruptedException err) {
                err.printStackTrace();
            }
            storage.add(carcase);
            currentCount++;
            carcaseCount++;
            notifyAll();
        }
    }

    public Integer getCarcaseCount() {
        synchronized (this) {
            return currentCount;
        }
    }

    public Carcase getCarcase() {
        synchronized (this) {
            try {
                while (currentCount == 0) {
                    wait();
                }
            }
            catch (InterruptedException err) {
                err.printStackTrace();
            }
            Carcase carcase = storage.pollLast();
            currentCount--;
            notifyAll();
            return carcase;
        }
    }
    public boolean isFilled() {
        return (maxCount.equals(currentCount));
    }
}
