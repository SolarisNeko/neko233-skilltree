package com.neko233.skilltree.commons.core.cache.objects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Must concurrent
 *
 * @author SolarisNeko on 2022-12-14
 **/
public interface Cache<K, V> {

    /**
     * 是否需要刷新
     *
     * @return
     */
    boolean isNeedRefresh();

    Cache<K, V> isNeedRefresh(boolean value);

    /**
     * 刷新
     */
    void refresh();

    Cache<K, V> put(K key, V value);

    Cache<K, V> putAll(Map<? extends K, ? extends V> map);

    /**
     * 缓存失效
     *
     * @param key 关键
     * @return this
     */
    Cache<K, V> invalidate(K key);

    Cache<K, V> invalidateAll(Iterable<K> keys);

    Cache<K, V> invalidateAll();

    V get(K key);

    ConcurrentMap<K, V> getAll();

    void cleanUp();

    default V getOrCreate(K key, Supplier<V> supplier) {
        return getOrCreate(key, supplier.get());
    }

    default V getOrCreate(K key, V newValue) {
        V v = get(key);
        if (v == null) {
            put(key, newValue);
        }
        return newValue;
    }

    /**
     * 快照
     *
     * @return concurrent map
     */
    default ConcurrentMap<K, V> cacheSnapshot() {
        return new ConcurrentHashMap<>(getAll());
    }

    /**
     * 是否需要刷盘
     *
     * @return true = flush
     */
    boolean isNeedFlush();

    Cache<K, V> isNeedFlush(boolean isNeedFlush);

    /**
     * 刷盘机制
     */
    default void flush() {
        ConcurrentMap<K, V> snapshot = cacheSnapshot();
        flushCallable(snapshot);
    }

    /**
     * 监听刷盘, 接管刷盘
     *
     * @param snapshot 快照
     */
    void flushCallable(ConcurrentMap<K, V> snapshot);


    Cache<K, V> addFlushListener(FlushListener<K, V> listener);

    Cache<K, V> removeFlushListener(FlushListener<K, V> listener);


    /**
     * 过期时间配置
     *
     * @param invalidateTime 过期时间
     * @param timeUnit       时间单位
     * @return this
     */
    Cache<K, V> invalidateTime(int invalidateTime, TimeUnit timeUnit);

    /**
     * @param maxSize 数量
     * @return this
     */
    Cache<K, V> invalidateMaxSize(int maxSize);
}
