package com.neko233.skilltree.commons.core.cache.objects.ref;


import java.lang.ref.SoftReference;

/**
 * @author SolarisNeko on 2022-12-12
 **/
public class TimestampSoftRef<V> extends SoftReference<V> {

    private volatile long visitMs;

    public TimestampSoftRef(V data) {
        super(data);
        this.visitMs = System.currentTimeMillis();
    }

    public long getVisitMs() {
        return visitMs;
    }

    private TimestampSoftRef<V> refreshTime() {
        visitMs = System.currentTimeMillis();
        return this;
    }

    @Override
    public V get() {
        refreshTime();
        return super.get();
    }
}
