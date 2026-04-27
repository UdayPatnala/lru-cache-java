package com.roadmap.lru;

public class LRUCacheTest {

    public static void main(String[] args) {
        shouldEvictLeastRecentlyUsedItem();
        shouldUpdateExistingKey();
        shouldTrackSizeCorrectly();
        shouldHandleCapacityOne();
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

    private static void shouldTrackSizeCorrectly() {
        LRUCache cache = new LRUCache(3);

        assertTrue(cache.isEmpty(), "Expected cache to be empty initially");
        assertEquals(0, cache.size(), "Expected size 0");

        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(2, cache.size(), "Expected size 2 after two puts");

        cache.put(3, 30);
        cache.put(4, 40);
        assertEquals(3, cache.size(), "Expected size to stay at capacity");
        assertEquals(3, cache.getCapacity(), "Expected capacity 3");
    }

    private static void shouldHandleCapacityOne() {
        LRUCache cache = new LRUCache(1);

        cache.put(1, 10);
        assertEquals(10, cache.get(1), "Expected key 1");

        cache.put(2, 20);
        assertEquals(-1, cache.get(1), "Expected key 1 evicted");
        assertEquals(20, cache.get(2), "Expected key 2 present");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ". Expected " + expected + " but got " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}