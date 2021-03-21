import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class BlockingQueue<T> {
    T[] contents;
    int capacity;
    Semaphore enq = new Semaphore(this.capacity - this.contents.length);
    Semaphore deq = new Semaphore(this.contents.length);

    public BlockingQueue(int capacity) {
        contents = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        enq.acquire();
        // dodadi element vo contents
        this.contents[this.contents.length] = item;
        deq.release();
    }


    public T dequeue() throws InterruptedException {
        deq.acquire();
        // izbrisi element od contents
        this.contents = Arrays.copyOfRange(this.contents, 0, this.contents.length - 2);
        enq.release();

        return this.contents[this.contents.length];
    }
}