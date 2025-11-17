package prep1125;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LruCacheWithTTL<K,V> {

    private class CacheEntry{
        V value;
        long expireAt;
        CacheEntry(V value, long expireAt){
            this.value=value;
            this.expireAt=expireAt;
        }
        boolean isExpired(){
            return expireAt!=0 && System.currentTimeMillis()> expireAt;
        }
    }
    private final int maxSize;
    private final Map<K , CacheEntry> cache;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();

    public LruCacheWithTTL(int maxSize, long cleanUpIntervalMs){
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K , CacheEntry> eldest){
                return size() > maxSize;
            }
        };
        cleanup.scheduleAtFixedRate(this::removeExpiredEntries,cleanUpIntervalMs,cleanUpIntervalMs, TimeUnit.MILLISECONDS);
    }
    public void set(K key, V value, long ttlMs) {
        lock.writeLock().lock();
        try {
            long expireAt = (ttlMs > 0) ? System.currentTimeMillis() + ttlMs : 0;
            cache.put(key, new CacheEntry(value, expireAt));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        lock.writeLock().lock(); // write lock — because LinkedHashMap modifies order on get
        try {
            CacheEntry entry = cache.get(key);
            if (entry == null) return null;

            if (entry.isExpired()) {
                cache.remove(key);
                return null;
            }
            return entry.value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void removeExpiredEntries() {
        lock.writeLock().lock();
        try {
            Iterator<Map.Entry<K, CacheEntry>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().isExpired()) {
                    iterator.remove();
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void shutdown() {
        cleanup.shutdown();
    }

    public static void main(String[] args) {
        LruCacheWithTTL<String, String> cache = new LruCacheWithTTL<>(2, 1000);
        cache.set("a", "1", 5000);
        cache.set("b", "2", 5000);
        cache.get("a"); // a now becomes most recently used
        cache.set("c", "3", 5000); // b should be evicted (LRU)
        System.out.println(cache.get("b")); // null
        System.out.println(cache.get("a")); // 1
        cache.shutdown();
    }

}
