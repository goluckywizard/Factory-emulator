package factory;

import java.util.Date;

public class CarDealer implements Runnable{
    CarStorage carStorage;
    Date start;

    public CarDealer(CarStorage carStorage, Integer dealerID, boolean LogSale, Date start) {
        this.carStorage = carStorage;
        this.dealerID = dealerID;
        this.LogSale = LogSale;
        this.start = start;
    }

    Integer dealerID;
    Integer sleepTime = 500;
    boolean LogSale = true;

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Car car = carStorage.getCar();
            Date date = new Date();
            if (LogSale)
                System.out.println(date.getTime()-start.getTime()+": Dealer:"+dealerID+": Car"+car.getID()+" (Carcase:"+car.carcase.ID
                +", Engine: "+car.engine.ID+", Accessory:"+car.accessory.ID+")\n");
        }
    }
}
