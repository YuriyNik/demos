package prep1125;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExpiringLRUCache <K, V> {
    private final int capacity;
    private final long ttlMillis;
    private final Map<K, Node<K,V>> map = new HashMap<>();
    private final DoublyLinkedList<K,V> list = new DoublyLinkedList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    static class Node<K,V> {
        K key;
        V value;
        long expireAt;
        Node<K,V> prev, next;
        Node(K k, V v, long exp) { key = k; value = v; expireAt = exp; }
        boolean isExpired() { return System.currentTimeMillis() > expireAt; }
    }

    static class DoublyLinkedList<K,V> {
        private Node<K,V> head = null;
        private Node<K,V> tail = null;

        void addFirst(Node<K,V> node) {
            if (head == null) {
                head = tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }

        void moveToFront(Node<K,V> node) {
            if (node == head) return;
            remove(node);
            addFirst(node);
        }

        Node<K,V> removeLast() {
            if (tail == null) return null;
            Node<K,V> oldTail = tail;
            remove(oldTail);
            return oldTail;
        }

        void remove(Node<K,V> node) {
            if (node.prev != null) node.prev.next = node.next;
            else head = node.next;
            if (node.next != null) node.next.prev = node.prev;
            else tail = node.prev;
            node.prev = node.next = null;
        }
    }

    public ExpiringLRUCache(int capacity, long ttlMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
    }

    public V get(K key) {
        lock.writeLock().lock();
        try {
            Node<K,V> node = map.get(key);
            if (node == null || node.isExpired()) {
                if (node != null) {
                    list.remove(node);
                    map.remove(key);
                }
                return null;
            }
            list.moveToFront(node);
            return node.value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            if (map.containsKey(key)) {
                Node<K,V> old = map.get(key);
                list.remove(old);
                map.remove(key);
            }
            Node<K,V> node = new Node<>(key, value, System.currentTimeMillis() + ttlMillis);
            list.addFirst(node);
            map.put(key, node);

            if (map.size() > capacity) {
                Node<K,V> lru = list.removeLast();
                map.remove(lru.key);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Simple test
    public static void main(String[] args) throws InterruptedException {
        ExpiringLRUCache<Integer, String> cache = new ExpiringLRUCache<>(2, 1000);

        cache.put(1, "A");
        cache.put(2, "B");
        Thread.sleep(500);
        System.out.println(cache.get(1)); // A

        Thread.sleep(600);
        System.out.println(cache.get(1)); // null (expired)
        cache.put(3, "C");
        System.out.println(cache.get(2)); // B
        System.out.println(cache.get(3)); // C
    }
}