package com.neko233.toolchain.metrics.runtime;


import com.neko233.toolchain.common.base.DataSizeUtils233;

import java.io.Serializable;

/**
 * 运行时信息，包括内存总大小、已用大小、可用大小等
 *
 * @author looly
 */
public class JvmRuntimeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Runtime currentRuntime = Runtime.getRuntime();

    /**
     * 获得运行时对象
     *
     * @return {@link Runtime}
     */
    public final Runtime getRuntime() {
        return currentRuntime;
    }

    /**
     * 获得JVM最大内存
     *
     * @return 最大内存
     */
    public final long getMaxMemory() {
        return currentRuntime.maxMemory();
    }

    /**
     * 获得JVM已分配内存
     *
     * @return 已分配内存
     */
    public final long getTotalMemory() {
        return currentRuntime.totalMemory();
    }

    /**
     * 获得JVM已分配内存中的剩余空间
     *
     * @return 已分配内存中的剩余空间
     */
    public final long getFreeMemory() {
        return currentRuntime.freeMemory();
    }

    /**
     * 获得JVM最大可用内存
     *
     * @return 最大可用内存
     */
    public final long getUsableMemory() {
        return currentRuntime.maxMemory() - currentRuntime.totalMemory() + currentRuntime.freeMemory();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Max Memory:         ").append(DataSizeUtils233.toHumanFormatByByte(getMaxMemory()));
        builder.append("Total Memory:       ").append(DataSizeUtils233.toHumanFormatByByte(getTotalMemory()));
        builder.append("Free Memory:        ").append(DataSizeUtils233.toHumanFormatByByte(getFreeMemory()));
        builder.append("Usable Memory:      ").append(DataSizeUtils233.toHumanFormatByByte(getUsableMemory()));

        return builder.toString();
    }
}