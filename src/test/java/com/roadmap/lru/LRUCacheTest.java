package com.roadmap.lru;

public class LRUCacheTest {

    public static void main(String[] args) {
        shouldEvictLeastRecentlyUsedItem();
        shouldUpdateExistingKey();
        System.out.println("All LRUCache tests passed.");
    }

    private static void shouldEvictLeastRecentlyUsedItem() {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(10, cache.get(1), "Expected key 1 to be present");

        cache.put(3, 30);

        assertEquals(-1, cache.get(2), "Expected key 2 to be evicted");
        assertEquals(10, cache.get(1), "Expected key 1 to remain");
        assertEquals(30, cache.get(3), "Expected key 3 to be present");
    }

    private static void shouldUpdateExistingKey() {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(1, 15);

        assertEquals(15, cache.get(1), "Expected updated value for key 1");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ". Expected " + expected + " but got " + actual);
        }
    }
}