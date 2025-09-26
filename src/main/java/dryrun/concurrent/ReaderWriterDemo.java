package dryrun.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderWriterDemo {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static int count = 0;

    public static void main(String[] args) {
        // Запустим несколько читателей
        for (int i = 1; i <= 3; i++) {
            int id = i;
            new Thread(() -> read(id)).start();
        }

        // Запустим писателя чуть позже
        new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            write(1);
        }).start();

        // Ещё один читатель после писателя
        new Thread(() -> {
            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
            read(4);
        }).start();
    }

    private static void read(int readerId) {
        System.out.println("Reader " + readerId + " пытается читать...");
        lock.readLock().lock();
        try {
            System.out.println("Reader " + readerId + " читает: count=" + count);
            Thread.sleep(1000); // эмуляция чтения
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Reader " + readerId + " закончил чтение.");
            lock.readLock().unlock();
        }
    }

    private static void write(int writerId) {
        System.out.println("Writer " + writerId + " пытается писать...");
        lock.writeLock().lock();
        try {
            count++;
            System.out.println("Writer " + writerId + " пишет: count=" + count);
            Thread.sleep(1000); // эмуляция записи
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Writer " + writerId + " закончил запись.");
            lock.writeLock().unlock();
        }
    }
}
