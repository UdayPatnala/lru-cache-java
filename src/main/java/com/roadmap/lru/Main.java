package com.roadmap.lru;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Generic LRU Cache Demo ---");
        // Using String keys and Integer values, wrapped in a thread-safe decorator
        Cache<String, Integer> cache = new ThreadSafeCache<>(new LRUCache<>(2));

        cache.put("One", 10);
        cache.put("Two", 20);

        System.out.println("get('One') = " + cache.get("One"));

        // 'One' is most recently used. Inserting 'Three' evicts 'Two'
        cache.put("Three", 30);

        System.out.println("get('Two') = " + cache.get("Two"));   // Should be null (evicted)
        System.out.println("get('Three') = " + cache.get("Three")); // Should be 30
        System.out.println("Cache size: " + cache.size());          // Should be 2
    }
}