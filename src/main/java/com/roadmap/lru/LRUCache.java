package com.roadmap.lru;

import java.util.HashMap;
import java.util.Map;

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

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToFront(node);
        return node.value;
    }

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
