package com.neko233.skilltree.commons.core.dataStruct.bit;

import com.neko233.skilltree.commons.dataStruct.bit.BitOperateUtils233;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class BitOperateUtils233Test {

    @Test
    public void test_base() {
        int num = 0;

        System.out.println("Original number: " + num);

        System.out.println("Setting bit " + BitOperateUtils233.setBit(num, 0));
        System.out.println("Get bit " + BitOperateUtils233.getBit(num, 0));
        System.out.println("Clearing bit " + BitOperateUtils233.clearBit(num, 0));

        System.out.println("Setting bits  " + BitOperateUtils233.setBit(num, 1, 2));
        System.out.println("Clearing bits  " + BitOperateUtils233.clearBit(num, 1, 2));
        System.out.println("Get bit " + BitOperateUtils233.getBit(num, 0));
    }
}