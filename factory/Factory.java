package factory;

import threadpool.ThreadPool;

import java.io.*;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;

public class Factory {
    /*Comparator<Worker> idComparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker o1, Worker o2) {
            int k = 0;
            if (o1.isOnWork)
                k += 2;
            if (o2.isOnWork)
                k -= 2;
            if (o1.workerID > o2.workerID)
                k += 1;
            if (o1.workerID < o2.workerID)
                k -= 1;
            return k;
        }
    };*/
    Integer workersCount;
    Integer dealerCount;
    Integer accessoriesSuppliersCount;
    Integer CarcaseStorageSize;
    Integer EngineStorageSize;
    Integer AccessoryStorageSize;
    Integer CarStorageSize;
    boolean isLog;

    //Worker[] workers;

    void textConfigure() {
        try {
            String path = new File(".").getParent();
            InputStream input =  this.getClass().getResourceAsStream("/config.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(input);
            //FileReader fileReader = new FileReader("/config.txt");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            //System.out.println(line);
            do {
                String parts[];
                parts = line.split("[\\W]+");

                if (parts[0].equals("CarcaseStorageSize"))
                    CarcaseStorageSize = Integer.valueOf(parts[1]);
                if (parts[0].equals("EngineStorageSize"))
                    EngineStorageSize = Integer.valueOf(parts[1]);
                if (parts[0].equals("CarStorageSize"))
                    CarStorageSize = Integer.valueOf(parts[1]);
                if (parts[0].equals("AccessorySuppliers"))
                    accessoriesSuppliersCount = Integer.valueOf(parts[1]);
                if (parts[0].equals("AccessoryStorageSize"))
                    AccessoryStorageSize = Integer.valueOf(parts[1]);
                if (parts[0].equals("Workers"))
                    workersCount = Integer.valueOf(parts[1]);
                if (parts[0].equals("Dealers"))
                    dealerCount = Integer.valueOf(parts[1]);
                if (parts[0].equals("LogSale")) {
                    isLog = parts[1].equals("true");
                }

                line = bufferedReader.readLine();
            } while (line != null);
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }

    Controller controller;
    CarcaseSupplier carcaseSupplierRunnable;
    EngineSupplier engineSupplierRunnable;
    CarDealer[] dealers;
    AccessorySupplier[] accessorySupplierRunnable;
    CarcaseStorage carcaseStorage;
    EngineStorage engineStorage;
    AccessoryStorage accessoryStorage;
    CarStorage carStorage;
    ThreadPool threadPool;

    public Factory() {
        //Integer workersCount = 2;
        //Integer dealerCount = 3;
        //PriorityBlockingQueue<Worker> workersQueue = new PriorityBlockingQueue<>(workersCount, idComparator);
        //FactoryUI ui = new FactoryUI();
        textConfigure();
        //System.out.println(CarStorageSize+" "+CarcaseStorageSize);
        Worker[] workers = new Worker[workersCount];
        dealers = new CarDealer[dealerCount];

        threadPool = new ThreadPool(4);
        //Integer accessoriesSuppliersCount = 4;
        carcaseStorage = new CarcaseStorage(CarcaseStorageSize);
        engineStorage = new EngineStorage(EngineStorageSize);
        accessoryStorage = new AccessoryStorage(AccessoryStorageSize);
        carStorage = new CarStorage(CarStorageSize);

        for (int i = 0; i < workersCount; i++) {
            workers[i] = new Worker(accessoryStorage, engineStorage, carcaseStorage, carStorage, i);
        }
        controller = new Controller(workers, carStorage, threadPool);
        Date date = new Date();
        for (int i = 0; i < dealerCount; i++) {
            dealers[i] = new CarDealer(carStorage, i, isLog, date);
        }

        carcaseSupplierRunnable = new CarcaseSupplier(carcaseStorage);
        engineSupplierRunnable = new EngineSupplier(engineStorage);
        accessorySupplierRunnable = new AccessorySupplier[accessoriesSuppliersCount];
        for (int i = 0; i < accessoriesSuppliersCount; ++i) {
            accessorySupplierRunnable[i] = new AccessorySupplier(accessoryStorage);
        }
    }

    public Controller getController() {
        return controller;
    }

    public CarcaseSupplier getCarcaseSupplierRunnable() {
        return carcaseSupplierRunnable;
    }

    public EngineSupplier getEngineSupplierRunnable() {
        return engineSupplierRunnable;
    }

    public CarDealer[] getDealers() {
        return dealers;
    }

    public AccessorySupplier[] getAccessorySupplierRunnable() {
        return accessorySupplierRunnable;
    }

    public CarcaseStorage getCarcaseStorage() {
        return carcaseStorage;
    }

    public EngineStorage getEngineStorage() {
        return engineStorage;
    }

    public AccessoryStorage getAccessoryStorage() {
        return accessoryStorage;
    }

    public CarStorage getCarStorage() {
        return carStorage;
    }

    public void start() {
        Thread carcaseSupplier = new Thread(carcaseSupplierRunnable);
        Thread engineSupplier = new Thread(engineSupplierRunnable);
        Thread[] accessoriesSuppliers = new Thread[accessoriesSuppliersCount];
        Thread[] dealersThreads = new Thread[dealerCount];
        for (int i = 0; i < accessoriesSuppliersCount; ++i) {
            accessoriesSuppliers[i] = new Thread(accessorySupplierRunnable[i]);
        }
        Thread controlCars = new Thread(controller);
        for (int i = 0; i < dealerCount; i++) {
            dealersThreads[i] = new Thread(dealers[i]);
        }

        controlCars.start();
        carcaseSupplier.start();
        engineSupplier.start();
        for (int i = 0; i < accessoriesSuppliersCount; ++i) {
            accessoriesSuppliers[i].start();
        }
        for (int i = 0; i < dealerCount; i++) {
            dealersThreads[i].start();
        }
    }
}
