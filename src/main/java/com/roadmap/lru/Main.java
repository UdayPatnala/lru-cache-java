package com.roadmap.lru;

public class Main {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(2, 20);

        System.out.println("get(1) = " + cache.get(1));

        cache.put(3, 30);

        System.out.println("get(2) = " + cache.get(2));
        System.out.println("get(3) = " + cache.get(3));
        System.out.println("Cache size: " + cache.size());
    }
}