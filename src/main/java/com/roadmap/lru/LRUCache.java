package com.roadmap.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * An O(1) Least Recently Used cache backed by a HashMap and a doubly linked list.
 * <p>
 * The HashMap provides constant-time key lookup while the doubly linked list
 * maintains access order so the least recently used entry can be evicted in O(1).
 */
public class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head;
    private final Node tail;

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
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

        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Returns the value for the given key, or -1 if absent.
     * Accessing a key promotes it to most-recently-used.
     */
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToFront(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     * If the cache is at capacity, the least recently used entry is evicted.
     */
    public void put(int key, int value) {
        Node existing = map.get(key);
        if (existing != null) {
            existing.value = value;
            moveToFront(existing);
            return;
        }

        if (map.size() == capacity) {
            Node lru = removeLast();
            if (lru != null) {
                map.remove(lru.key);
            }
        }

        Node created = new Node(key, value);
        addToFront(created);
        map.put(key, created);
    }

    /** Returns the number of entries currently in the cache. */
    public int size() {
        return map.size();
    }

    /** Returns true if the cache contains no entries. */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /** Returns the maximum capacity of this cache. */
    public int getCapacity() {
        return capacity;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addToFront(node);
    }

    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node removeLast() {
        if (tail.prev == head) {
            return null;
        }
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}
