package com.neko233.skilltree.commons.core.dataStruct.bytes;

/**
 * Byte 数组
 *
 * @author SolarisNeko
 */
public class ArrayByte233 {

    // 记录当前写入到多少位
    int index;
    // 数组指针. mode = fix length / auto scale
    private byte[] arrayByte;

    private ArrayByte233(int capacity) {
        arrayByte = new byte[capacity];
        index = 0;
    }

    public static ArrayByte233 CreateBytes(int capacity) {
        return new ArrayByte233(capacity);
    }

    /**
     * 将数字转换为byte数组
     */
    public static byte[] Number2byte(long val) {
        return new byte[]{
                (byte) ((val >> 56) & 0xFF),
                (byte) ((val >> 48) & 0xFF),
                (byte) ((val >> 40) & 0xFF),
                (byte) ((val >> 32) & 0xFF),
                (byte) ((val >> 24) & 0xFF),
                (byte) ((val >> 16) & 0xFF),
                (byte) ((val >> 8) & 0xFF),
                (byte) (val & 0xFF)
        };
    }

    public static byte[] Number2byte(int val) {
        return new byte[]{
                (byte) ((val >> 24) & 0xFF),
                (byte) ((val >> 16) & 0xFF),
                (byte) ((val >> 8) & 0xFF),
                (byte) (val & 0xFF)
        };
    }

    public static byte[] Number2byte(short val) {
        return new byte[]{
                (byte) ((val >> 8) & 0xFF),
                (byte) (val & 0xFF)
        };
    }

    //向数组中追加内容
    public ArrayByte233 AppendNumber(long val) {
        byte[] arr = Number2byte(val);
        AppendBytesWithAutoScale(arr);
        return this;
    }

    public ArrayByte233 AppendNumber(int val) {
        byte[] arr = Number2byte(val);
        AppendBytesWithAutoScale(arr);
        return this;
    }

    public ArrayByte233 AppendNumber(short val) {
        byte[] arr = Number2byte(val);
        AppendBytesWithAutoScale(arr);
        return this;
    }

    public ArrayByte233 AppendNumber(byte val) {
        byte[] arr = new byte[]{val};
        AppendBytesWithAutoScale(arr);
        return this;
    }

    //追加byte数组
    public ArrayByte233 AppendBytes(byte[] arr) {
        for (byte i = 0; i < arr.length; i++) {
            this.arrayByte[index + i] = arr[i];
        }

        index += arr.length;
        return this;
    }

    //追加byte数组
    public ArrayByte233 AppendBytesWithAutoScale(byte[] appendByteArray) {
        int newLength = index + appendByteArray.length;
        if (newLength > arrayByte.length) {
            byte[] destBytes = new byte[newLength];
            System.arraycopy(this.arrayByte, 0, destBytes, 0, index);
            this.arrayByte = destBytes;
        }
        for (byte i = 0; i < appendByteArray.length; i++) {
            arrayByte[index + i] = appendByteArray[i];
        }

        index += appendByteArray.length;
        return this;
    }

    /**
     * 获取 bytes 的总和
     *
     * @return 总和
     */
    public int GetSum() {
        int ret = 0;
        for (int i = 0; i < this.arrayByte.length; i++) {
            ret += this.arrayByte[i];
        }
        return ret;
    }

}