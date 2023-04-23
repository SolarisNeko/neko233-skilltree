package com.neko233.toolchain.idGenerator.snowflake;

import com.neko233.toolchain.idGenerator.IdGenerator;
import com.neko233.toolchain.idGenerator.IdGeneratorException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * SnowFlake = 64 bit, 8 Byte = 1 bit (不用) + 41 bit Timestamp + 10 bit workerId + 12 bit SequenceId
 *
 * @author SolarisNeko on 2022-12-31
 */
@Slf4j
public class IdGeneratorBySnowflake implements IdGenerator {

    public static final int RETRY_MAX_COUNT_IN_SAME_MS = 3; // 最大重试次数
    public static final long DEFAULT_START_TWEPOCH_2020_01_01 = 1577808000000L; // 默认开始的 twepoch | 2020-01-01 00:00:00.000

    private final String businessName;
    // 10位的工作机器id
    private final long workerId;    // 工作id 10 bit
    // 开始时间, 毫秒级别的时间截
    private final long twepoch;
    // 长度为5位
    private final long workerIdBits = 10L;
    // workerId 最大值
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 序列号id长度
    private final long sequenceBits = 12L;
    // 序列号最大值 = 低 sequenceBits 位
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    // 工作id需要左移的位数，12位
    private final long workerIdShift = sequenceBits;
    // 时间戳, 需要左移位数 10bit(workerId) + 12 bit(SequenceId) = << 22 bit
    private final long timestampLeftShift = workerIdBits + sequenceBits;
    // 12bit sequenceId | 每 1ms 重置为0
    private long sequence = 0;
    // 上次时间戳，初始值为负数
    private long lastTimestamp = -1L;
    public IdGeneratorBySnowflake(String businessName, long workerId) {
        this(businessName, workerId, DEFAULT_START_TWEPOCH_2020_01_01);
    }

    /**
     * constructor
     *
     * @param businessName 业务名称
     * @param workerId     worker ID
     * @param twepoch      业务开始的时间戳
     */
    public IdGeneratorBySnowflake(String businessName, long workerId, long twepoch) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        log.info(String.format("[IdGenerator-SnowFlake] workerId = %d  starting. timestamp left shift bits = %d, workerId bits = %d, sequence bits = %d, ",
                workerId, timestampLeftShift, workerIdBits, sequenceBits));

        this.businessName = businessName;
        this.workerId = workerId;
        this.twepoch = twepoch;
    }

    public long getWorkerId() {
        return workerId;
    }


    // 下一个ID生成算法
    @Override
    public synchronized Long nextId() throws IdGeneratorException {
        long currentMs = getCurrentMs();

        // 获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (currentMs < lastTimestamp) {
            log.error("时钟回滚了. clock is moving backwards. Rejecting requests until {}. currentMs = {}", lastTimestamp, currentMs);
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", currentMs));
        }

        // 同一毫秒内, sequence 递增. 否则重置 sequence
        if (lastTimestamp == currentMs) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                currentMs = untilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        // refresh
        lastTimestamp = currentMs;

        return ((currentMs - twepoch) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    @Override
    public String getName() {
        return businessName;
    }

    @Override
    public List<Long> nextIds(int count) throws IdGeneratorException {
        List<Long> idList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Long e = nextId();
            if (e == null) {
                continue;
            }
            idList.add(e);
        }
        return idList;
    }

    @Override
    public boolean cacheId(int count) throws IdGeneratorException {
        return false;
    }

    // 获取时间戳，并与上次时间戳比较
    private long untilNextMillis(long lastTimestamp) throws IdGeneratorException {
        long timestamp = getCurrentMs();
        int count = 0;
        while (timestamp <= lastTimestamp && count < RETRY_MAX_COUNT_IN_SAME_MS) {
            timestamp = getCurrentMs();
            count++;
        }
        if (count >= RETRY_MAX_COUNT_IN_SAME_MS) {
            throw new IdGeneratorException("你的本地时钟一直回滚. your local clock is crazy because it still back to previous timestamp");
        }
        return timestamp;
    }

    // 获取系统时间戳
    private long getCurrentMs() {
        return System.currentTimeMillis();
    }


}