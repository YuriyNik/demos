package prep1125;

import java.util.Map;
import java.util.concurrent.*;

// Key-value store with TTL and thread-safety
public class InMemoryCache <K ,V> {
    // Each entry stores the value and expiration time
    private static class CacheValue<V> {
        V value;
        long expireAt; // timestamp in ms; if expireAt = 0 => no TTL

        CacheValue(V value, long expireAt) {
            this.value = value;
            this.expireAt = expireAt;
        }

        boolean isExpired() {
            return expireAt != 0 && System.currentTimeMillis() > expireAt;
        }
    }
    private final Map <K , CacheValue<V>> storage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleanup = Executors.newSingleThreadScheduledExecutor();

    public InMemoryCache(long cleanUpIntervalMs) {
        // Background cleaner to remove expired entries
        cleanup.scheduleAtFixedRate(this::removeExpiredKeys, cleanUpIntervalMs, cleanUpIntervalMs, TimeUnit.MILLISECONDS);
    }
    // Set with optional TTL (ttlMs = 0 means no expiration)
    public void set(K key, V value, long ttlMs) {
        long expireAt = (ttlMs>0) ? System.currentTimeMillis() + ttlMs : 0;
        storage.put(key , new CacheValue<>(value , expireAt));
    }

    public V get(K key){
        CacheValue<V> cached = storage.get(key);
        if (cached==null) {
            return null;
        }
        if (cached.isExpired()) {
            storage.remove(key);
            return null;
        }
        return cached.value;
    }

    public void remove(K key){
        storage.remove(key);
    }
    private void removeExpiredKeys(){
        for(Map.Entry<K, CacheValue<V>> entry : storage.entrySet()){
            if (entry.getValue().isExpired()) {
                storage.remove(entry.getKey());
            }
        }
    }

    public void shutdown(){
        cleanup.shutdown();
    }


    public static void main(String[] args) throws InterruptedException {
        InMemoryCache<String, String> cache = new InMemoryCache<>(1000);
        cache.set("name", "Yury" , 2000);
        System.out.println(cache.get("name"));//Yury
        Thread.sleep(1900);
        System.out.println(cache.get("name"));//Yury
        Thread.sleep(95);
        System.out.println(cache.get("name"));//Null
        cache.shutdown();
    }
}
