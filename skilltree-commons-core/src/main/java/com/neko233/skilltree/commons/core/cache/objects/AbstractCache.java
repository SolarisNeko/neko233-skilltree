package com.neko233.skilltree.commons.core.cache.objects;

import com.neko233.skilltree.commons.core.cache.objects.metrics.SimpleCacheMetrics;
import com.neko233.skilltree.commons.core.cache.objects.metrics.CacheMetrics;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author SolarisNeko on 2022-12-14
 **/
public abstract class AbstractCache<K, V> implements Cache<K, V>, CacheMetrics {

    protected CacheMetrics cacheMetrics = new SimpleCacheMetrics();


    /**
     * ----------------------- Cache Metrics ------------------------
     */
    @Override
    public CacheMetrics addHitCount(long count) {
        return cacheMetrics.addHitCount(count);
    }

    @Override
    public long getHitCount() {
        return cacheMetrics.getHitCount();
    }

    @Override
    public CacheMetrics addMissCount(long count) {
        return cacheMetrics.addMissCount(count);
    }

    @Override
    public long getMissCount() {
        return cacheMetrics.getMissCount();
    }

    @Override
    public CacheMetrics addEvictionCount(long count) {
        return cacheMetrics.addEvictionCount(count);
    }

    @Override
    public Long getEvictionCount() {
        return cacheMetrics.getEvictionCount();
    }


    /**
     * ----------------------- Cache ------------------------
     */
    @Override
    public boolean isNeedRefresh() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public Cache<K, V> isNeedFlush(boolean isNeedFlush) {
        return null;
    }

    @Override
    public Cache<K, V> invalidate(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> invalidateAll(Iterable<K> keys) {
        for (K key : keys) {
            invalidate(key);
        }
        return this;
    }

    @Override
    public Cache<K, V> invalidateAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConcurrentMap<K, V> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cleanUp() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getOrCreate(K key, Supplier<V> supplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getOrCreate(K key, V newValue) {
        return Cache.super.getOrCreate(key, newValue);
    }

    @Override
    public boolean isNeedFlush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flushCallable(ConcurrentMap<K, V> snapshot) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> isNeedRefresh(boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> addFlushListener(FlushListener<K, V> listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> removeFlushListener(FlushListener<K, V> listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> invalidateTime(int invalidateTime, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cache<K, V> invalidateMaxSize(int maxSize) {
        throw new UnsupportedOperationException();
    }
}
