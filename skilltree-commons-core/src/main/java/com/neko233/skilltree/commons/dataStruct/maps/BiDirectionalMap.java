package com.neko233.skilltree.commons.dataStruct.maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * 双流向 Map
 *
 * @author SolarisNeko
 */
public class BiDirectionalMap<K, V> {

    private final Map<K, V> forwardMap = new ConcurrentHashMap<>();
    private final Map<V, K> backMap = new ConcurrentHashMap<>();

    public BiDirectionalMap<K, V> put(K key, V value) {
        forwardMap.put(key, value);
        backMap.put(value, key);
        return this;
    }

    public BiDirectionalMap<K, V> merge(K key, V value,
                                        BiFunction<V, V, V> forwardBi,
                                        BiFunction<K, K, K> backupBi
    ) {
        forwardMap.merge(key, value, forwardBi);
        backMap.merge(value, key, backupBi);
        return this;
    }


    public V getForward(K key) {
        return getForward(key, null);
    }

    public V getForward(K key, V defaultValue) {
        V v = forwardMap.get(key);
        if (v == null) {
            return defaultValue;
        }
        return v;
    }

    public K getBack(V value) {
        return getBack(value, null);
    }

    public K getBack(V value, K defaultValue) {
        K v = backMap.get(value);
        if (v == null) {
            return defaultValue;
        }
        return v;
    }

    public int size() {
        return Math.min(forwardMap.size(), backMap.size());
    }
}
