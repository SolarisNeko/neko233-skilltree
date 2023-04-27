package com.neko233.skilltree.commons.core.cache.objects.impl;


import com.neko233.skilltree.commons.core.annotation.ThreadSafe;
import com.neko233.skilltree.commons.core.cache.objects.ref.TimestampSoftRef;
import com.neko233.skilltree.commons.core.cache.objects.AbstractCache;
import com.neko233.skilltree.commons.core.cache.objects.Cache;
import com.neko233.skilltree.commons.core.cache.objects.FlushListener;
import com.neko233.skilltree.commons.core.cache.objects.metrics.CacheMetrics;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * [Timestamp LRU Cache]
 * spend 1 thread to async Scheduler
 *
 * @author SolarisNeko on 2022-12-12
 **/
@ThreadSafe
public class TimestampLruCache<K, V> extends AbstractCache<K, V> {

    // data
    private final ConcurrentMap<K, TimestampSoftRef<V>> cacheMap = new ConcurrentHashMap<>();
    // listener
    private final List<FlushListener<K, V>> flushListenerList = new CopyOnWriteArrayList<>();
    /**
     * scheduler for cache eviction
     */
    private final ScheduledExecutorService evictionScheduler = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName(TimestampLruCache.class.getSimpleName() + "-" + System.currentTimeMillis());
        return thread;
    });
    // invalidate settings
    public volatile long invalidateMs = TimeUnit.HOURS.toMillis(1);
    public volatile long invalidateMaxSize = 100_0000;
    // state
    private volatile boolean isNeedRefresh = true;
    private volatile boolean isNeedFlush = false;

    /**
     * Constructor
     */
    public TimestampLruCache() {
        evictionScheduler.scheduleAtFixedRate(() -> {
            // 刷盘
            flush();

            // invalidate
            long nowMs = System.currentTimeMillis();
            Set<K> toInvalidateKeys = cacheMap.entrySet().stream().filter(entry -> {
                TimestampSoftRef<V> value = entry.getValue();
                if (value == null) {
                    return true;
                }
                return (nowMs - value.getVisitMs()) > invalidateMs;
            }).map(Map.Entry::getKey).collect(Collectors.toSet());

            this.invalidateAll(toInvalidateKeys);
            this.cacheMetrics.addEvictionCount(toInvalidateKeys.size());
        }, 0, 5, TimeUnit.SECONDS);
    }


    private V getByMetrics(TimestampSoftRef<V> vTimestampSoftRef) {
        if (vTimestampSoftRef == null) {
            this.cacheMetrics.addMissCount(1);
            return null;
        }
        this.cacheMetrics.addHitCount(1);
        return vTimestampSoftRef.get();
    }


    // ------------------------ Cache API -----------------------------------

    /**
     * cache 过期时间限制
     *
     * @param invalidateTime 失效时长
     * @param timeUnit       单位
     * @return this
     */
    @Override
    public Cache<K, V> invalidateTime(int invalidateTime, TimeUnit timeUnit) {
        this.invalidateMs = timeUnit.toMillis(invalidateTime);
        return this;
    }

    @Override
    public Cache<K, V> invalidateMaxSize(int maxSize) {
        this.invalidateMaxSize = maxSize;
        return this;
    }


    /**
     * flush listener
     *
     * @param listener L
     * @return this
     */
    @Override
    public Cache<K, V> addFlushListener(FlushListener<K, V> listener) {
        flushListenerList.add(listener);
        return this;
    }

    @Override
    public Cache<K, V> removeFlushListener(FlushListener<K, V> listener) {
        flushListenerList.remove(listener);
        return this;
    }

    @Override
    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    @Override
    public Cache<K, V> isNeedRefresh(boolean value) {
        this.isNeedRefresh = value;
        return this;
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    public Cache<K, V> put(K key, V value) {
        cacheMap.put(key, new TimestampSoftRef<>(value));
        return this;
    }

    @Override
    public Cache<K, V> putAll(Map<? extends K, ? extends V> map) {
        return super.putAll(map);
    }

    @Override
    public Cache<K, V> isNeedFlush(boolean isNeedFlush) {
        this.isNeedFlush = isNeedFlush;
        return this;
    }

    @Override
    public Cache<K, V> invalidate(K key) {
        cacheMap.remove(key);
        return this;
    }

    @Override
    public Cache<K, V> invalidateAll(Iterable<K> keys) {
        return super.invalidateAll(keys);
    }

    @Override
    public Cache<K, V> invalidateAll() {
        cacheMap.clear();
        return this;
    }

    @Override
    public V get(K key) {
        TimestampSoftRef<V> vTimestampSoftRef = cacheMap.get(key);
        return getByMetrics(vTimestampSoftRef);
    }

    @Override
    public ConcurrentMap<K, V> getAll() {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
        cacheMap.forEach((k, vRef) -> {
            V value = vRef.get();
            if (value == null) {
                return;
            }
            map.put(k, value);
        });
        return map;
    }

    @Override
    public void cleanUp() {
        invalidateAll();
    }

    @Override
    public boolean isNeedFlush() {
        return this.isNeedFlush;
    }

    @Override
    public void flushCallable(ConcurrentMap<K, V> snapshot) {
        for (FlushListener<K, V> flushListener : flushListenerList) {
            flushListener.handle(snapshot);
        }
    }

    @Override
    public V getOrCreate(K key, Supplier<V> supplier) {
        return super.getOrCreate(key, supplier);
    }

    @Override
    public V getOrCreate(K key, V newValue) {
        TimestampSoftRef<V> valueRef = cacheMap.get(key);
        if (valueRef == null) {
            this.put(key, newValue);
            return newValue;
        }
        return valueRef.get();
    }

    @Override
    public CacheMetrics snapshotMetrics() {
        return this.cacheMetrics.snapshotMetrics();
    }

}
