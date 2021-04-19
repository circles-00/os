package PettaZadaca;


import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Vinegar {
    static Semaphore cPool = new Semaphore(2);
    static Semaphore oPool = new Semaphore(2);
    static Semaphore hPool = new Semaphore(4);
    static Semaphore cHere = new Semaphore(0);
    static Semaphore oHere = new Semaphore(0);
    static Semaphore hHere = new Semaphore(0);
    static Semaphore ready = new Semaphore(0);
    static Semaphore done = new Semaphore(0);
    static Semaphore leave = new Semaphore(0);
    static Semaphore lock = new Semaphore(1);

    static int numH = 0;

    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new C());
            threads.add(new H());
            threads.add(new H());
            threads.add(new O());
        }
        // run all threads in background

        for (Thread t: threads){
            t.start();
        }

        // after all of them are started, wait each of them to finish for maximum 2_000 ms
        for (Thread t: threads){
            t.join(2000);
        }

        // for each thread, terminate it if it is not finished
        for (Thread t: threads){
            if (t.isAlive()) {
                t.interrupt();
                System.out.println("Possible deadlock!");
            }

        }
        System.out.println("Process finished.");
    }

    static class C extends Thread {

        public void execute() throws InterruptedException {
            // at most 2 atoms should print this in parallel
            cPool.acquire();
            cHere.release();
            System.out.println("C here.");
            // after all atoms are present, they should start with the bonding process together
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process
            System.out.println("C done.");
            done.release();
            leave.acquire();
            cPool.release();
        }

        @Override
        public void run() {
            try {
                this.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class H extends Thread {

        public void execute() throws InterruptedException {
            // at most 4 atoms should print this in parallel
            hPool.acquire();
            System.out.println("H here.");
            // after all atoms are present, they should start with the bonding process together
            lock.acquire();
            numH++;
            if (numH == 4) {
                numH = 0;
                lock.release();
                cHere.acquire(2);
                oHere.acquire(2);
                hHere.acquire(3);
                ready.release(7);
                System.out.println("Molecule bonding.");
                Thread.sleep(100);// this represent the bonding process
                System.out.println("H done.");
                done.acquire(7);
                leave.release(7);
                // only one atom should print the next line, representing that the molecule is created
                System.out.println("Molecule created.");
            } else {
                lock.release();
                hHere.release();
                ready.acquire();
                System.out.println("Molecule bonding.");
                Thread.sleep(100);// this represent the bonding process
                System.out.println("H done.");
                done.release();
                leave.acquire();
            }
            hPool.release();
        }

        @Override
        public void run() {
            try {
                this.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class O extends Thread {

        public void execute() throws InterruptedException {
            // at most 2 atoms should print this in parallel
            oPool.acquire();
            oHere.release();
            System.out.println("O here.");
            // after all atoms are present, they should start with the bonding process together
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process
            System.out.println("O done.");
            done.release();
            leave.acquire();
            oPool.release();
        }

        @Override
        public void run() {
            try {
                this.execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


