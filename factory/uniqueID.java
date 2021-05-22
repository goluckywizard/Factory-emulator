package factory;

public class uniqueID {
    static int ID = 0;
    public static synchronized int getID() {
        return ID++;
    }
}
