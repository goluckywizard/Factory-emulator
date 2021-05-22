package factory;

import java.util.ArrayDeque;
import java.util.Deque;

public class AccessoryStorage {
    Deque<Accessory> storage;
    Integer accessoryCount = 0;
    Integer currentCount = 0;
    Integer maxCount;

    /*AccessoryStorage() {
        storage = new ArrayDeque<>();
    }*/

    public AccessoryStorage(Integer maxCount) {
        storage = new ArrayDeque<>();
        this.maxCount = maxCount;
    }

    public void addAccessories(Accessory accessory) {
        synchronized (this) {
            try {
                while (isFilled()) {
                    wait();
                }
            }
            catch (InterruptedException err) {
                err.printStackTrace();
            }
            storage.add(accessory);
            currentCount++;
            accessoryCount++;
            notifyAll();
        }
    }

    public Integer getAccessoryCount() {
        synchronized (this) {
            return currentCount;
        }
    }

    public Accessory getAccessory() {
        synchronized (this) {
            try {
                while (currentCount == 0) {
                    wait();
                }
            }
            catch (InterruptedException err) {
                err.printStackTrace();
            }
            Accessory accessory = storage.pollLast();
            currentCount--;
            notifyAll();
            return accessory;
        }
    }
    public boolean isFilled() {
        return (maxCount.equals(currentCount));
    }
}
