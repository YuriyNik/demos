package dryrun.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueLock<T> implements BlockingQueue<T> {

    private final Object[] items;
    private int head = 0; // индекс для извлечения
    private int tail = 0; // индекс для вставки
    private int count = 0; // текущее количество элементов

    private final Lock lock ;
    private final Condition isFull ;
    private final Condition notEmpty ;

    public BlockingQueueLock(int capacity){
       this.items = new Object[capacity];
       this.lock = new ReentrantLock();
        isFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    @Override
    public void put (T item) throws InterruptedException {
        lock.lock();
        try{
            while (count==items.length){
                isFull.await();
            }
            items[tail] = item;
            tail = (tail+1)%items.length;
            count++;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T item = (T) items[head];
            items[head] = null;
            head = (head + 1) % items.length;
            count--;
            isFull.signal();
            return item;
        } finally {
            lock.unlock();
        }

    }
    @Override
    public int size(){
        lock.lock();
        try{
            return count;
        } finally {
        lock.unlock();
        }

    }
}

