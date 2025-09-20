package dryrun.queue;

public interface BlockingQueue<T> {
    void put (T item) throws InterruptedException;
    T take() throws InterruptedException;
    int size();
}
