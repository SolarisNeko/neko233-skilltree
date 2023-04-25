package com.neko233.toolchain.commons.core.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils233 {

    private MapUtils233() {
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?, ?> collection) {
        return !isEmpty(collection);
    }


    public static <K, V> void putDataIntoList(Map<K, List<V>> map, K key, V data) {
        List<V> dataList = map.computeIfAbsent(key, k -> new ArrayList<V>());
        dataList.add(data);
    }

    public static <K, V> Map<K, V> of(Object... objs) {
        if (objs == null) {
            return new HashMap<>(0);
        }
        if (objs.length % 2 != 0) {
            throw new IllegalArgumentException("your map data is not 2 Multiple ratio");
        }

        Map<K, V> map = new HashMap<>(objs.length / 2);
        for (int i = 0; i < objs.length; i += 2) {
            map.put((K) objs[i], (V) objs[i + 1]);
        }
        return map;
    }
}
