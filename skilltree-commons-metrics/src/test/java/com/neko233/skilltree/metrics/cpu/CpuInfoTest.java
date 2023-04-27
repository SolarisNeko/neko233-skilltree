package com.neko233.skilltree.metrics.cpu;

import org.junit.Test;

public class CpuInfoTest {

    CpuInfo cpuInfo = new CpuInfo();

    @Test
    public void getCpuNum() {
        Integer getCpuNum = cpuInfo.getCpuNum();
//        System.out.println(getCpuNum);
    }


    @Test
    public void getToTal() {
        double getToTal = cpuInfo.getToTal();
//        System.out.println(getToTal);
    }


    @Test
    public void getSys() {
        double getSys = cpuInfo.getSys();
//        System.out.println(getSys);
    }


    @Test
    public void getUser() {
        double getUser = cpuInfo.getUser();
//        System.out.println(getUser);
    }


    @Test
    public void getWait() {
        double getWait = cpuInfo.getWait();
//        System.out.println(getWait);
    }


    @Test
    public void getFree() {
        double getFree = cpuInfo.getFree();
//        System.out.println(getFree);
    }


    @Test
    public void getCpuModel() {
        String getCpuModel = cpuInfo.getCpuModel();
//        System.out.println(getCpuModel);
    }


    @Test
    public void getTicks() {
        CpuTicks getTicks = cpuInfo.getTicks();
//        System.out.println(getTicks);
    }


    @Test
    public void getUsed() {
        double getUsed = cpuInfo.getUsed();
//        System.out.println(getUsed);
    }
}