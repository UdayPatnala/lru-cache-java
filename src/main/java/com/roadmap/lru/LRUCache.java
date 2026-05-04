package com.roadmap.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * An O(1) Least Recently Used cache backed by a HashMap and a doubly linked list.
 * <p>
 * The HashMap provides constant-time key lookup while the doubly linked list
 * maintains access order so the least recently used entry can be evicted in O(1).
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Creates a new LRU cache with the given capacity.
     *
     * @param capacity maximum number of entries; must be positive
     * @throws IllegalArgumentException if capacity is not positive
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.map = new HashMap<>();

        // Using null keys/values for dummy sentinel nodes
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Returns the value for the given key, or null if absent.
     * Accessing a key promotes it to most-recently-used.
     */
    @Override
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }
        moveToFront(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     * If the cache is at capacity, the least recently used entry is evicted.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not supported");
        }
        
        Node<K, V> existing = map.get(key);
        if (existing != null) {
            existing.value = value;
            moveToFront(existing);
            return;
        }

        if (map.size() == capacity) {
            Node<K, V> lru = removeLast();
            if (lru != null && lru.key != null) {
                map.remove(lru.key);
            }
        }

        Node<K, V> created = new Node<>(key, value);
        addToFront(created);
        map.put(key, created);
    }

    /** Returns the number of entries currently in the cache. */
    @Override
    public int size() {
        return map.size();
    }

    /** Returns true if the cache contains no entries. */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /** Returns the maximum capacity of this cache. */
    @Override
    public int getCapacity() {
        return capacity;
    }

    private void moveToFront(Node<K, V> node) {
        removeNode(node);
        addToFront(node);
    }

    private void addToFront(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node<K, V> removeLast() {
        if (tail.prev == head) {
            return null;
        }
        Node<K, V> node = tail.prev;
        removeNode(node);
        return node;
    }
}
