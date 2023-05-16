package com.neko233.skilltree.commons.core.dataStruct.bit;

import com.neko233.skilltree.commons.dataStruct.bit.BitMap;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class BitMapTest {

    @Test
    public void test() {
        BitMap bitMap = new BitMap(1024);

        int target = 3;
        bitMap.mark(target);
        boolean contains = bitMap.isMark(target);
        System.out.println(contains);

        byte[] serialize = BitMap.serialize(bitMap);
        System.out.println(serialize);

        BitMap deserialize = BitMap.deserialize(serialize);
        System.out.println(deserialize);

        boolean mark = deserialize.isMark(target);
        System.out.println(mark);

        System.out.println(deserialize.getAllMark());
    }

}