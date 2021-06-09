package factory;

import java.util.Comparator;

public class Worker implements Runnable {
    AccessoryStorage accessoryStorage;
    EngineStorage engineStorage;
    CarcaseStorage carcaseStorage;
    CarStorage carStorage;
    boolean isOnWork = false;
    Integer workerID;
    Controller controller;

    public Worker(AccessoryStorage accessoryStorage, EngineStorage engineStorage, CarcaseStorage carcaseStorage, CarStorage carStorage, Integer workerID) {
        this.accessoryStorage = accessoryStorage;
        this.engineStorage = engineStorage;
        this.carcaseStorage = carcaseStorage;
        this.carStorage = carStorage;
        this.workerID = workerID;
    }

    public Worker(AccessoryStorage accessoryStorage, EngineStorage engineStorage, CarcaseStorage carcaseStorage, CarStorage carStorage, Integer workerID, Controller controller) {
        this.accessoryStorage = accessoryStorage;
        this.engineStorage = engineStorage;
        this.carcaseStorage = carcaseStorage;
        this.carStorage = carStorage;
        this.workerID = workerID;
        this.controller = controller;
    }

    @Override
    public void run() {
        Accessory accessory = accessoryStorage.getAccessory();
        Engine engine = engineStorage.getEngine();
        Carcase carcase = carcaseStorage.getCarcase();
        Car car = new Car(accessory, carcase, engine);
        System.out.println("New car: " +car.getID()+ " engine: "+ engine.ID +" accessory: "+accessory.ID+"\n");
        carStorage.addCar(car);
        isOnWork = false;
        synchronized (controller) {
            controller.notify();
        }
    }

}
