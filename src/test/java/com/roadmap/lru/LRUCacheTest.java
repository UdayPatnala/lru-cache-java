package com.roadmap.lru;

public class LRUCacheTest {

    public static void main(String[] args) throws InterruptedException {
        shouldEvictLeastRecentlyUsedItem();
        shouldUpdateExistingKey();
        shouldTrackSizeCorrectly();
        shouldHandleCapacityOne();
        shouldWorkWithGenerics();
        shouldBeThreadSafe();
        System.out.println("All LRUCache tests passed.");
    }

    private static void shouldEvictLeastRecentlyUsedItem() {
        Cache<Integer, Integer> cache = new LRUCache<>(2);

        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(Integer.valueOf(10), cache.get(1), "Expected key 1 to be present");

        cache.put(3, 30);

        assertEquals(null, cache.get(2), "Expected key 2 to be evicted");
        assertEquals(Integer.valueOf(10), cache.get(1), "Expected key 1 to remain");
        assertEquals(Integer.valueOf(30), cache.get(3), "Expected key 3 to be present");
    }

    private static void shouldUpdateExistingKey() {
        Cache<Integer, Integer> cache = new LRUCache<>(2);

        cache.put(1, 10);
        cache.put(1, 15);

        assertEquals(Integer.valueOf(15), cache.get(1), "Expected updated value for key 1");
    }

    private static void shouldTrackSizeCorrectly() {
        Cache<Integer, Integer> cache = new LRUCache<>(3);

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
        Cache<Integer, Integer> cache = new LRUCache<>(1);

        cache.put(1, 10);
        assertEquals(Integer.valueOf(10), cache.get(1), "Expected key 1");

        cache.put(2, 20);
        assertEquals(null, cache.get(1), "Expected key 1 evicted");
        assertEquals(Integer.valueOf(20), cache.get(2), "Expected key 2 present");
    }

    private static void shouldWorkWithGenerics() {
        Cache<String, String> cache = new LRUCache<>(2);
        
        cache.put("A", "Apple");
        cache.put("B", "Banana");
        
        assertEquals("Apple", cache.get("A"), "Expected Apple");
        
        cache.put("C", "Cherry"); // Evicts "B"
        
        assertEquals(null, cache.get("B"), "Expected B to be evicted");
        assertEquals("Cherry", cache.get("C"), "Expected Cherry");
    }

    private static void shouldBeThreadSafe() throws InterruptedException {
        // Use ThreadSafeCache decorator
        Cache<Integer, String> cache = new ThreadSafeCache<>(new LRUCache<>(1000));
        
        Runnable task = () -> {
            for (int i = 0; i < 500; i++) {
                cache.put(i, "Value" + i);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        // As both threads put the same 500 keys, the size should be exactly 500.
        // Without thread safety, the internal map and doubly linked list could corrupt.
        assertEquals(500, cache.size(), "Expected size 500 after concurrent inserts");
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError(message + ". Expected " + expected + " but got " + actual);
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