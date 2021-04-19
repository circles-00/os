package mk.ukim.finki.os.synchronization.exam14.march.aloh3;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

import mk.ukim.finki.os.synchronization.ProblemExecution;
import mk.ukim.finki.os.synchronization.TemplateThread;

public class AluminiumHydroxide {
    static Semaphore oPool;
    static Semaphore hPool;
    static Semaphore alPool;
    static Semaphore oHere;
    static Semaphore hHere;
    static Semaphore ready;
    static Semaphore done;
    static Semaphore readyALOHA;
    static Semaphore doneALOHA;
    static Semaphore leave;

    public static void init() {
        oPool = new Semaphore(3);
        hPool = new Semaphore(3);
        alPool = new Semaphore(1);
        oHere = new Semaphore(0);
        hHere = new Semaphore(0);
        ready = new Semaphore(0);
        done = new Semaphore(0);
        readyALOHA = new Semaphore(0);
        doneALOHA = new Semaphore(0);
        leave = new Semaphore(0);
    }

    public static class Hydrogen extends TemplateThread {

        public Hydrogen(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            hPool.acquire();
            hHere.release();
            ready.acquire();
            state.bondOH();
            done.release();
            readyALOHA.acquire();
            state.bondAlOH3();
            doneALOHA.release();
            leave.acquire();
            hPool.release();
        }

    }

    public static class Oxygen extends TemplateThread {

        public Oxygen(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            oPool.acquire();
            oHere.release();
            ready.acquire();
            state.bondOH();
            done.release();
            readyALOHA.acquire();
            state.bondAlOH3();
            doneALOHA.release();
            leave.acquire();
            oPool.release();
        }

    }

    public static class Aluminium extends TemplateThread {

        public Aluminium(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            alPool.acquire();
            oHere.acquire(3);
            hHere.acquire(3);
            ready.release(6);
            done.acquire(6);
            readyALOHA.release(6);
            state.bondAlOH3();
            doneALOHA.acquire(6);
            leave.release(6);
            state.validate();
            alPool.release();
        }

    }

    static AluminiumHydroxideState state = new AluminiumHydroxideState();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            int numRuns = 1;
            int numScenarios = 300;

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numScenarios; i++) {
                Oxygen o = new Oxygen(numRuns);
                Hydrogen h = new Hydrogen(numRuns);
                threads.add(o);
                if (i % 3 == 0) {
                    Aluminium al = new Aluminium(numRuns);
                    threads.add(al);
                }
                threads.add(h);
            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
