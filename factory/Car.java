package factory;

public class Car {
    Accessory accessory;
    Carcase carcase;
    Engine engine;
    Integer ID;

    public Car(Accessory accessory, Carcase carcase, Engine engine) {
        this.accessory = accessory;
        this.carcase = carcase;
        this.engine = engine;
        ID = uniqueID.getID();
    }

    public Integer getID() {
        return ID;
    }
}
