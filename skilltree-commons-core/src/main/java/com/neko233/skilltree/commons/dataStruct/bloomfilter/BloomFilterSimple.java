package com.neko233.skilltree.commons.dataStruct.bloomfilter;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 布隆过滤器 v1.Simple
 * you can't clear BloomFilter because it is stateful, you can CAS another BloomFilter again.
 *
 * @param <T> Object Type
 * @author SolarisNeko
 */
public final class BloomFilterSimple<T> implements BloomFilterApi<T> {

    private final byte[] bloomSet;
    private final int generateBloomHashCount;
    private final MessageDigest md;
    private int size;

    /**
     * Constructor
     */
    public BloomFilterSimple(int capacity) {
        this(capacity, 5);
    }

    /**
     * Constructor
     *
     * @param capacity  容量
     * @param hashCount hash count
     */
    public BloomFilterSimple(int capacity, int hashCount) {
        this.bloomSet = new byte[Math.max(1000, capacity)];
        this.generateBloomHashCount = Math.max(1, hashCount);
        this.size = 0;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Error : MD5 Hash not found");
        }
    }

    /* Function to check is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Function to get size of objects added
     */
    public int getSize() {
        return size;
    }

    /**
     * Function to get hash - MD5
     */
    private int getHash(int i) {
        md.reset();
        byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
        md.update(bytes, 0, bytes.length);
        return Math.abs(new BigInteger(1, md.digest()).intValue()) % (bloomSet.length - 1);
    }

    @Override
    public void add(Object obj) {
        int[] bloomHashIndexSet = calculateObjectBloomHashSet(obj);
        for (int hashIndex : bloomHashIndexSet) {
            this.bloomSet[hashIndex] = 1;
        }
        this.size++;
    }

    @Override
    public boolean isMightContains(T object) {
        int[] bloomHashBits = calculateObjectBloomHashSet(object);
        for (int idx : bloomHashBits) {
            if (bloomSet[idx] == 1)
                return true;
        }

        return false;
    }

    /**
     * calc object hash set
     *
     * @param obj 对象
     * @return hashCode[]
     */
    private int[] calculateObjectBloomHashSet(Object obj) {
        int[] multiHashValueArray = new int[generateBloomHashCount];
        multiHashValueArray[0] = getHash(obj.hashCode());
        for (int i = 1; i < generateBloomHashCount; i++) {
            int bloomHashBit = multiHashValueArray[i - 1];
            int hash = getHash(bloomHashBit);
            multiHashValueArray[i] = hash;
        }
        return multiHashValueArray;
    }

}
