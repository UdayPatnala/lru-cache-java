package com.roadmap.lru;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LRUCacheTest {

    @Test
    void shouldEvictLeastRecentlyUsedItem() {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(10, cache.get(1));

        cache.put(3, 30);

        assertEquals(-1, cache.get(2));
        assertEquals(10, cache.get(1));
        assertEquals(30, cache.get(3));
    }

    @Test
    void shouldUpdateExistingKey() {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 10);
        cache.put(1, 15);

        assertEquals(15, cache.get(1));
    }
}
