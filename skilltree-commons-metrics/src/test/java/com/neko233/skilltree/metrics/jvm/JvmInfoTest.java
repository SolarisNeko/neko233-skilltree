package com.neko233.skilltree.metrics.jvm;

import org.junit.Test;

public class JvmInfoTest {

    public static final JvmInfo jvmInfo = new JvmInfo();

    @Test
    public void getName() {
        String getName = jvmInfo.getName();
//        System.out.println(getName);
    }

    @Test
    public void getVersion() {
        String getVersion = jvmInfo.getVersion();
//        System.out.println(getVersion);
    }

    @Test
    public void getVendor() {
        String getVendor = jvmInfo.getVendor();
//        System.out.println(getVendor);
    }

    @Test
    public void getInfo() {
        String getInfo = jvmInfo.getInfo();
//        System.out.println(getInfo);
    }
}