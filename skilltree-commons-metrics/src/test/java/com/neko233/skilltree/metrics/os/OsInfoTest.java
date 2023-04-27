package com.neko233.skilltree.metrics.os;

import org.junit.Test;

public class OsInfoTest {

    OsInfo osInfo = new OsInfo();

    @Test
    public void getArch() {
        String getArch = osInfo.getArch();
//        System.out.println(getArch);
    }

    @Test
    public void getName() {
        String getName = osInfo.getName();
//        System.out.println(getName);
    }

    @Test
    public void getVersion() {
        String getVersion = osInfo.getVersion();
//        System.out.println(getVersion);
    }

    @Test
    public void getFileSeparator() {
        String getFileSeparator = osInfo.getFileSeparator();
//        System.out.println(getFileSeparator);
    }

    @Test
    public void getLineSeparator() {
        String getLineSeparator = osInfo.getLineSeparator();
//        System.out.println(getLineSeparator);
    }

    @Test
    public void getPathSeparator() {
        String getPathSeparator = osInfo.getPathSeparator();
//        System.out.println(getPathSeparator);
    }
}