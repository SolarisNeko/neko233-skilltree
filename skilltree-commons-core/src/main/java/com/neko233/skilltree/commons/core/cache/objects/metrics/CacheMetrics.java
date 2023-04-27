package com.neko233.skilltree.commons.core.cache.objects.metrics;

/**
 * @author SolarisNeko on 2022-12-14
 **/
public interface CacheMetrics {

    /**
     * 命中
     */
    CacheMetrics addHitCount(long count);

    long getHitCount();

    /**
     * 没命中
     */
    CacheMetrics addMissCount(long count);

    long getMissCount();

    /**
     * 逐出
     */
    CacheMetrics addEvictionCount(long count);

    Long getEvictionCount();


    /**
     * 快照状态
     *
     * @return
     */
    CacheMetrics snapshotMetrics();

}
