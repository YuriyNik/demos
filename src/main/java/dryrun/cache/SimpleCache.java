package dryrun.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache<K,V> implements Cache<K,V>{

    private final Map<K,CacheEntry<V>> store = new ConcurrentHashMap<>();
    private final long ttlMillis;

    public SimpleCache(long ttlMillis){
        this.ttlMillis=ttlMillis;
    }

    @Override
    public void put(K key, V value) {
        long expiryTime = System.currentTimeMillis() + ttlMillis;
        store.put(key, new CacheEntry<>(value, expiryTime));
    }
    @Override
    public V get(K key){
        CacheEntry<V> entry = store.get(key);
        if (entry==null) return null;
        if (System.currentTimeMillis()> entry.expiryTime) {
            store.remove(key);
            return null;
        }
        return entry.value;
    }
    @Override
    public void remove(K key){
        store.remove(key);
    }
    @Override
    public int size(){
        return store.size();
    }

    @Override
    public void stop() {

    }

    private static class CacheEntry<V>{
        V value;
        long expiryTime;
        CacheEntry(V value, long expiryTime){
            this.value=value;
            this.expiryTime=expiryTime;
        }
    }
}
