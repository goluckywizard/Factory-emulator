package factory;

public class AccessorySupplier implements Runnable{
    AccessoryStorage storage;
    Integer timeWait = 1000;

    public void setStorage(AccessoryStorage storage) {
        this.storage = storage;
    }
    public AccessorySupplier(AccessoryStorage storage) {
        this.storage = storage;
    }

    public void setTimeWait(Integer timeWait) {
        this.timeWait = timeWait;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(timeWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Accessory accessory = new Accessory(uniqueID.getID());
            //System.out.println("accessory:" + accessory.ID);
            storage.addAccessories(accessory);
        }
    }
}
