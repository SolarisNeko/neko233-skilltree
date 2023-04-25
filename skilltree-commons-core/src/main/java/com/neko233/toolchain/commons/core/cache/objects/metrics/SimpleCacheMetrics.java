package com.neko233.toolchain.commons.core.cache.objects.metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko on 2022-12-14
 **/
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleCacheMetrics implements CacheMetrics {

    private long hitCount = 0;
    private long missCount = 0;
    private long evictionCount = 0;

    @Override
    public CacheMetrics addHitCount(long count) {
        this.hitCount += count;
        return this;
    }

    @Override
    public long getHitCount() {
        return hitCount;
    }

    @Override
    public CacheMetrics addMissCount(long count) {
        this.missCount += count;
        return this;
    }

    @Override
    public long getMissCount() {
        return missCount;
    }

    @Override
    public CacheMetrics addEvictionCount(long count) {
        this.evictionCount += count;
        return this;
    }

    @Override
    public Long getEvictionCount() {
        return this.evictionCount;
    }

    @Override
    public CacheMetrics snapshotMetrics() {
        return SimpleCacheMetrics.builder()
                .hitCount(this.hitCount)
                .missCount(this.missCount)
                .evictionCount(this.evictionCount)
                .build();
    }
}
