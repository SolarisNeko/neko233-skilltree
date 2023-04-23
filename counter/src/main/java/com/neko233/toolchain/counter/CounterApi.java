package com.neko233.toolchain.counter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * counter is composite 3 part = id, type, count <br/>
 * refresh == del
 * if refresh_ms == -1, it means forever
 *
 * @author SolarisNeko
 */
public interface CounterApi {

    /**
     * 永久不过期
     */
    int NEXT_REFRESH_MS_FOREVER_FLAG = -1;

    static boolean isForever(long nextRefreshMs) {
        return Objects.equals(NEXT_REFRESH_MS_FOREVER_FLAG, nextRefreshMs);
    }

    static boolean isNeedRefresh(Long nextRefreshMs) {
        if (nextRefreshMs == null) {
            return false;
        }
        if (nextRefreshMs == -1) {
            return false;
        }
        return System.currentTimeMillis() > nextRefreshMs;
    }

    /**
     * refresh
     */
    default boolean refresh(Object id, String type) {
        del(id, type);
        return true;
    }

    /**
     * get
     */
    default CounterData get(Object id, String type) {
        return get(String.valueOf(id), type);
    }

    CounterData get(String id, String type);

    default Number getValue(Object id, String type) {
        Map<String, Number> value = getValue(id, Collections.singleton(type));
        if (value == null || value.size() == 0) {
            return null;
        }
        return value.get(type);
    }

    /**
     * get value
     */
    default Map<String, Number> getValue(Object id, Collection<String> types) {
        return getValue(String.valueOf(id), types);
    }

    Map<String, Number> getValue(String id, Collection<String> types);

    /**
     * set
     */
    default boolean set(Object id, String type) {
        return set(id, type, 0);
    }

    default boolean set(Object id, String type, Number count) {
        return set(id, type, count, -1);
    }

    default boolean set(Object id, String type, Number count, int nextTime, TimeUnit nextTimeUnit) {
        return set(id, type, count, System.currentTimeMillis() + nextTimeUnit.toMillis(nextTime));
    }

    default boolean set(Object id, String type, Number count, long nextRefreshMs) {
        return set(id, Collections.singletonMap(type, count), nextRefreshMs);
    }

    default boolean set(Object id, Map<String, Number> typeCountMap, long nextRefreshMs) {
        return set(String.valueOf(id), typeCountMap, nextRefreshMs);
    }

    boolean set(String id, Map<String, Number> typeCountMap, long nextRefreshMs);

    /**
     * del
     */
    default boolean del(Object id) {
        return del(String.valueOf(id));
    }

    boolean del(String id);

    default boolean del(Object id, String type) {
        return del(id, Collections.singleton(type));
    }

    default boolean del(Object id, Collection<String> types) {
        return del(String.valueOf(id), types);
    }

    boolean del(String id, Collection<String> types);

    /**
     * minus 1
     */
    default boolean minus(Object id, String type) {
        return minus(id, type, 1);
    }

    default boolean minus(Object id, String type, Number minusCount) {
        if (minusCount == null) {
            return false;
        }
        return plus(id, type, -minusCount.longValue());
    }

    /**
     * plus 1
     */
    default boolean plus(Object id, String type) {
        return plus(id, type, 1);
    }

    default boolean plus(Object id, String type, Number addCount) {
        if (addCount == null) {
            return false;
        }
        CounterData data = get(id, type);
        if (data == null) {
            return false;
        }
        if (isNeedRefresh(data.getNextRefreshMs())) {
            refresh(id, type);
            return false;
        }
        if (data.getCount() == null) {
            return false;
        }
        set(id, type, data.getCount().longValue() + addCount.longValue(), data.getNextRefreshMs());
        return true;
    }


    /**
     * 过期时间点
     *
     * @param nextRefreshMs 下一次刷新的毫秒数
     */
    default boolean expireAtMs(Object id, String type, long nextRefreshMs) {
        return expireAtMs(String.valueOf(id), type, nextRefreshMs);
    }

    boolean expireAtMs(String id, String type, long nextRefreshMs);

    default boolean expireNext(Object id, String type, int nextTime, TimeUnit nextTimeUnit) {
        return expireAtMs(id, type, System.currentTimeMillis() + nextTimeUnit.toMillis(nextTime));
    }

}
