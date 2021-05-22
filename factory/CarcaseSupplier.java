package factory;

public class CarcaseSupplier implements Runnable {
    //boolean isStorageFilled = false;
    CarcaseStorage storage;
    Integer timeWait = 1000;

    public void setTimeWait(Integer timeWait) {
        this.timeWait = timeWait;
    }

    public void setStorage(CarcaseStorage storage) {
        this.storage = storage;
    }
    CarcaseSupplier(CarcaseStorage storage) {
        this.storage = storage;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(timeWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Carcase carcase = new Carcase(uniqueID.getID());
            //System.out.println("carcase:" + carcase.ID);
            storage.addCarcase(carcase);
        }
    }
}
