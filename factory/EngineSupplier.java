package factory;

public class EngineSupplier implements Runnable {
    EngineStorage storage;
    Integer timeWait = 1000;
    public void setStorage(EngineStorage storage) {
        this.storage = storage;
    }

    EngineSupplier(EngineStorage storage) {
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

            Engine engine = new Engine(uniqueID.getID());
            //System.out.println("engine:" + engine.ID);
            storage.addEngine(engine);
        }
    }
}
