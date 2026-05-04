package com.roadmap.lru;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread-safe decorator for the Cache interface.
 * Uses a ReentrantLock to ensure thread-safety across concurrent access.
 */
public class ThreadSafeCache<K, V> implements Cache<K, V> {
    
    private final Cache<K, V> delegate;
    private final Lock lock = new ReentrantLock();

    public ThreadSafeCache(Cache<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V get(K key) {
        lock.lock();
        try {
            return delegate.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        lock.lock();
        try {
            delegate.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return delegate.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        try {
            return delegate.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getCapacity() {
        lock.lock();
        try {
            return delegate.getCapacity();
        } finally {
            lock.unlock();
        }
    }
}
