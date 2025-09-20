package dryrun.cache;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLRUCacheWithTTL <K,V> implements Cache<K,V>{
    private final int capacity;
    private final long ttlMillis;
    private final ConcurrentHashMap<K, Node<K,V>> map = new ConcurrentHashMap<>();
    private final ConcurrentLinkedDeque<Node<K,V>> deque = new ConcurrentLinkedDeque<>();

    private final ReentrantLock lock = new ReentrantLock();

    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public ConcurrentLRUCacheWithTTL(int capacity, long ttlMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;

        // фоновая очистка просроченных элементов
        cleaner.scheduleAtFixedRate(this::cleanExpired, ttlMillis/2, ttlMillis/2, TimeUnit.MILLISECONDS);
    }
    @Override
    public V get(K key) {
        Node<K,V> node = map.get(key);
        if (node == null) return null;

        long now = System.currentTimeMillis();
        if (now - node.timestamp >= ttlMillis) {
            removeNode(node);
            return null;
        }

        // перемещаем в head для LRU
        deque.remove(node);
        deque.offerFirst(node);

        return node.value;
    }

    @Override
    public void remove(K key) {

    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void put(K key, V value) {
        try {
            lock.lock();
        long now = System.currentTimeMillis();
        Node<K,V> newNode = new Node<>(key, value, now);

        Node<K,V> existing = map.put(key, newNode);
        if (existing != null) {
            deque.remove(existing);
        }
        deque.offerFirst(newNode);


            if (map.size() >= capacity) {
                Node<K, V> tail;
                while ((tail = deque.pollLast()) != null) {
                    if (map.remove(tail.key, tail)) break; // CAS remove
                }
            }
        }finally {
            lock.unlock();
        }
    }

    private void removeNode(Node<K,V> node) {
        deque.remove(node);
        map.remove(node.key, node);
    }

    private void cleanExpired() {
        long now = System.currentTimeMillis();
        for (Node<K,V> node : deque) {
            if (now - node.timestamp >= ttlMillis) {
                removeNode(node);
            }
        }
    }
    @Override
    public void stop() {
        cleaner.shutdownNow();
    }

    private static class Node<K,V> {
        final K key;
        final V value;
        final long timestamp;

        Node(K key, V value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}
