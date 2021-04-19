package VtoraZadaca;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BasketballTournament {

    public static void main(String[] args) throws InterruptedException {
        HashSet<Player> threads = new HashSet<>();
        for (int i = 0; i < 120; i++) {
            Player p = new Player();
            threads.add(p);
        }
        // run all threads in background
        for (Player p:  threads){
            p.start();
        }
        // after all of them are started, wait each of them to finish for maximum 5_000 ms
        for (Player p:  threads){
            p.join(5000);
        }
        // for each thread, terminate it if it is not finished
        for (Player p: threads){
            if (p.isAlive()) {
                System.out.println("Possible deadlock!");
                p.interrupt();
            }
        }
        System.out.println("Tournament finished.");

    }
}

class Player extends Thread {

    static Semaphore vleziSala = new Semaphore(20);
    static Semaphore kabinaVlez = new Semaphore(10);
    static Semaphore ready = new Semaphore(0);

    static int numPlayers = 0;


    public void execute() throws InterruptedException {
        // at most 20 players should print this in parallel
        vleziSala.acquire();
        System.out.println("Player inside.");
        // at most 10 players may enter in the dressing room in parallel
        kabinaVlez.acquire();
        System.out.println("In dressing room.");
        Thread.sleep(10);// this represent the dressing time
        kabinaVlez.release();

        synchronized (Player.class) {
            numPlayers++;
            if (numPlayers == 20) {
                ready.release(20);
            }
        }

        ready.acquire();
        // after all players are ready, they should start with the game together
        System.out.println("Game started.");
        Thread.sleep(100);// this represent the game duration


        synchronized (Player.class) {
            numPlayers--;
            if (numPlayers == 0) {
                System.out.println("Player done.");
                // only one player should print the next line, representing that the game has finished
                System.out.println("Game finished.");
                vleziSala.release(20);
            } else
                System.out.println("Player done.");
        }


    }

    @Override
    public void run() {
        try{
            this.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
