package com.roadmap.lru;

/**
 * A generic interface for a cache.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> {
    
    /**
     * Returns the value for the given key, or null if absent.
     */
    V get(K key);

    /**
     * Inserts or updates a key-value pair.
     */
    void put(K key, V value);

    /**
     * Returns the number of entries currently in the cache.
     */
    int size();

    /**
     * Returns true if the cache contains no entries.
     */
    boolean isEmpty();

    /**
     * Returns the maximum capacity of this cache.
     */
    int getCapacity();
}
