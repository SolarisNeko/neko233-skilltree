package com.neko233.skilltree.commons.core.cache.objects;

import java.util.concurrent.ConcurrentMap;

/**
 * @author SolarisNeko on 2022-12-14
 **/
public interface FlushListener<K, V> {

    void handle(ConcurrentMap<K, V> snapshot);

}
