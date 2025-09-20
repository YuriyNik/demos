package dryrun.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueueSynchronized<T> implements BlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public BlockingQueueSynchronized(int capacity){
        this.capacity=capacity;
    }

    @Override
    public synchronized void put (T item) throws InterruptedException {
        while (queue.size()==capacity){
            wait();
        }
        queue.add(item);
        System.out.println("queue.add(item)="+item);
        notifyAll();
    }
    @Override
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }
        T item = queue.remove();
        notifyAll();
        return item;

    }
    @Override
    public int size(){
        return queue.size();
    }

}
