package com.neko233.skilltree.commons.core.dataStruct.bit;


import com.neko233.skilltree.commons.core.annotation.NotNull;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class BitMap implements Serializable {


    private int capacity;
    private byte[] bitMapDataStruct;

    public BitMap(int capacity) {
        this.capacity = capacity;

        // 拆成 n 组 byte[]. 一个 byte 占 8 bit == 2 ^ 3 次方
        bitMapDataStruct = createBitMapDataStruct(capacity);
    }


    @NotNull
    private static byte[] createBitMapDataStruct(int capacity) {
        return new byte[(capacity >> 3) + 1];
    }

    private BitMap(int capacity,
                   byte[] bitMapData) {
        this.capacity = capacity;
        this.bitMapDataStruct = bitMapData;
    }

    public void changeCapacity(int newCapacity) {
        List<Integer> allMark = getAllMark();
        Integer historyMaxNumber = allMark.stream().max(Integer::compare).orElse(0);

        if (historyMaxNumber > newCapacity) {
            throw new IllegalArgumentException("you can not scale in your bitMap");
        }

        byte[] bitMapDataStruct = createBitMapDataStruct(newCapacity);
        this.bitMapDataStruct = bitMapDataStruct;

        this.capacity = newCapacity;
    }

    // 标记
    public void mark(int num) {
        int index = num >> 3;
        int position = num & 0x07;
        bitMapDataStruct[index] |= 1 << position;
    }

    public boolean isNotMark(int num) {
        return !isMark(num);
    }

    public boolean isMark(int num) {
        int index = num >> 3;
        int position = num & 0x07;
        return (bitMapDataStruct[index] & (1 << position)) != 0;
    }

    public List<Integer> getAllMark() {

        List<Integer> of = new java.util.ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (isNotMark(i)) {
                continue;
            }
            of.add(i);

        }
        return of;
    }

    public static byte[] serialize(BitMap bitMap) {
        String data = new String(bitMap.bitMapDataStruct, StandardCharsets.UTF_8);
        return (bitMap.capacity + "@" + data).getBytes(StandardCharsets.UTF_8);
    }

    public static BitMap deserialize(byte[] bytes) {
        String s = new String(bytes, StandardCharsets.UTF_8);
        String[] split = s.split("@");
        if (split.length != 2) {
            throw new IllegalArgumentException("your bytes can not deserialize to BitMap. bytes to UTF-8 String = " + new String(bytes, StandardCharsets.UTF_8));
        }
        return new BitMap(Integer.valueOf(split[0]), split[1].getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public String toString() {
        return "BitMap{" +
                "bits=" + Arrays.toString(bitMapDataStruct) +
                ", capacity=" + capacity +
                '}';
    }
}
