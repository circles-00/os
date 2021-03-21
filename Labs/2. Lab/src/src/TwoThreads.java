import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


class ThreadClassNumbersLetters<E> implements Runnable {
    ArrayList<E> list;

    public ThreadClassNumbersLetters(ArrayList<E> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (E e : list) System.out.println(e);
    }
}

public class TwoThreads {

    public static void main(String[] args) throws InterruptedException {
        Semaphore s = new Semaphore(1);
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            list1.add(String.valueOf((char)(i + 65)));

        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            list2.add(i);

        ThreadClassNumbersLetters<String> thread1 = new ThreadClassNumbersLetters<String>(list1);
        ThreadClassNumbersLetters<Integer> thread2 = new ThreadClassNumbersLetters<Integer>(list2);

        s.acquire();
        thread2.run();
        s.release();

        s.acquire();
        thread1.run();
        s.release();

    }

}



