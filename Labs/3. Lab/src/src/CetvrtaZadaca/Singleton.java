package CetvrtaZadaca;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Singleton extends Thread {

    private static volatile Singleton singleton;
    static Semaphore one = new Semaphore(1);

    private Singleton() {

    }

    public static Singleton getInstance() throws InterruptedException {
        // TODO: 3/29/20 Synchronize this\
        one.acquire();
        singleton = new Singleton();

        return singleton;
    }

    @Override
    public void run() {
        try {
            getInstance();
            System.out.println("Instance ID: " + this.getId() + " was the fastest in creating the Singletone instance");
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        // TODO: 3/29/20 Simulate the scenario when multiple threads call the method getInstance
        HashSet<Singleton> threads = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Singleton s = new Singleton();
            threads.add(s);
        }

        for (Singleton s : threads) {
            s.start();
        }

        for (Singleton s : threads) {
            s.join(10);
            s.interrupt();
        }
    }

}