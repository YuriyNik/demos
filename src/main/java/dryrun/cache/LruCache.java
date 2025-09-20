package dryrun.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LruCache <K, V> implements Cache<K, V> {
    private final int capacity;
    private final long ttlMillis;
    private final Map<K, Node<K,V>> map;
    private Node<K,V> head;
    private Node<K,V> tail;
    private final ReentrantLock lock = new ReentrantLock();

    private final Thread cleanerThread;
    private volatile boolean running = true;



    public LruCache(int capacity, long ttlMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
        this.map = new HashMap<>();

        cleanerThread = new Thread(this::cleanerLoop);
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public V get(K key) {
        lock.lock();
        try {
            Node<K, V> node = map.get(key);
            if (node == null) return null;
            if (isExpired(node)) {
                removeNode(node);
                map.remove(key);
                return null;
            }
            moveToHead(node);
            return node.value;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        lock.lock();
        try {
        Node<K,V> node = map.get(key);
        long now = System.currentTimeMillis();
        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            Node<K,V> newNode = new Node<>(key, value, now);
            map.put(key, newNode);
            addToHead(newNode);
            if (map.size() > capacity) {
                map.remove(tail.key);
                removeTail();
            }
        }
        } finally {
            lock.unlock();
        }
    }

    public void stop() {
        running = false;
        cleanerThread.interrupt();
    }

    // 🔹 Фоновая очистка
    private void cleanerLoop() {
        try {
            while (running) {
                Thread.sleep(ttlMillis / 2); // проверяем периодически
                cleanExpired();
            }
        } catch (InterruptedException ignored) {}
    }
    private void cleanExpired() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            Node<K, V> current = tail;
            while (current != null) {
                if (now - current.timestamp >= ttlMillis) {
                    Node<K, V> toRemove = current;
                    current = current.prev;
                    map.remove(toRemove.key);
                    removeNode(toRemove);
                } else {
                    break; // т.к. LRU — дальше старее, можно остановиться
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean isExpired(Node<K, V> node) {
        return (System.currentTimeMillis() - node.timestamp) >= ttlMillis;
    }
    @Override
    public int size(){
        return map.size();
    }

    @Override
    public void remove(K key){
        map.remove(key);
    }


    private void moveToHead(Node<K,V> node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(Node<K,V> node) {
        node.prev = null;
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = node;
    }

    private void removeNode(Node<K,V> node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
    }

    private void removeTail() {
        if (tail == null) return;
        if (tail.prev != null) {
            tail.prev.next = null;
        } else {
            head = null;
        }
        tail = tail.prev;
    }

    private static class Node<K,V> {
        K key;
        V value;
        long timestamp;
        Node<K,V> prev;
        Node<K,V> next;

        Node(K key, V value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}