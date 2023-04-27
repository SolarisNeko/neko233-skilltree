package com.neko233.skilltree.metrics.runtime;

import org.junit.Test;

public class JvmRuntimeInfoTest {

    JvmRuntimeInfo jvmRuntimeInfo = new JvmRuntimeInfo();

    @Test
    public void getMaxMemory() {
        long getMaxMemory = jvmRuntimeInfo.getMaxMemory();
//        System.out.println(DataSizeUtils233.toHumanFormatByByte(getMaxMemory));
    }

    @Test
    public void getTotalMemory() {
        long getTotalMemory = jvmRuntimeInfo.getTotalMemory();
//        System.out.println(DataSizeUtils233.toHumanFormatByByte(getTotalMemory));
    }

    @Test
    public void getFreeMemory() {
        long getFreeMemory = jvmRuntimeInfo.getFreeMemory();
//        System.out.println(DataSizeUtils233.toHumanFormatByByte(getFreeMemory));
    }

    @Test
    public void getUsableMemory() {
        long getUsableMemory = jvmRuntimeInfo.getUsableMemory();
//        System.out.println(DataSizeUtils233.toHumanFormatByByte(getUsableMemory));
    }
}