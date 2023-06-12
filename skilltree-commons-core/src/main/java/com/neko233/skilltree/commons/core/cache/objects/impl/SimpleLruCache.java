package com.neko233.skilltree.commons.core.cache.objects.impl;

import com.neko233.skilltree.annotation.ThreadNotSafe;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Not thread safe
 * 当满了的时候, T 掉最先进来的
 *
 * @param <K> key
 * @param <V> value
 * @author SolarisNeko
 */
@ThreadNotSafe
public class SimpleLruCache<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;
    private final int maxSize;

    public SimpleLruCache(int maxSize) {
        super(maxSize + 1, 1.0F, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maxSize;
    }

}