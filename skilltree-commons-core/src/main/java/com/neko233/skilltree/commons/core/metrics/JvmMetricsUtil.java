package com.neko233.skilltree.commons.core.metrics;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author SolarisNeko on 2022-12-14
 **/
public class JvmMetricsUtil {

    /**
     * @return 当前堆大小
     */
    public static long getCurrentHeapSize() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * @return 最大大小
     */
    public static long getHeapMaxSizeByte() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * @return 空闲大小
     */
    public static long getHeapFreeSizeByte() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * @return GC 情况
     */
    public static List<GarbageCollectorMXBean> getGcMxBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans();
    }

}
