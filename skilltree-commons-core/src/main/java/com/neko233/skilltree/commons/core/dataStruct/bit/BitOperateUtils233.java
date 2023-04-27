package com.neko233.skilltree.commons.core.dataStruct.bit;

import java.util.ArrayList;
import java.util.List;

public class BitOperateUtils233 {



    /**
     * 获取指定位置的 bit 值
     *
     * @param num 原始数值
     * @param pos 要获取的 bit 位置，从右往左数，从 0 开始
     * @return 指定位置的 bit 值，0 或 1
     */
    public static int getBit(int num, int pos) {
        return (num >> pos) & 1;
    }

    public static List<Integer> getBitList(int num, int... pos) {
        List<Integer> objects = new ArrayList<>();
        for (int po : pos) {
            objects.add(po);
        }
        return objects;
    }

    /**
     * 批量将指定位置的 bit 置为 1
     *
     * @param num           原始数值
     * @param positionArray 要置为 1 的 bit 位置数组，从右往左数，从 0 开始
     * @return 置位后的数值
     */
    public static int setBit(int num, int... positionArray) {
        int tempNum = num;
        for (int pos : positionArray) {
            tempNum = tempNum | (1 << pos);
        }
        return tempNum;
    }

    /**
     * 批量将指定位置的 bit 置为 0
     *
     * @param num           原始数值
     * @param positionArray 要置为 0 的 bit 位置数组，从右往左
     */
    public static int clearBit(int num, int... positionArray) {
        int tempNum = num;
        for (int position : positionArray) {
            tempNum = tempNum & ~(1 << position);
        }
        return tempNum;
    }

}
