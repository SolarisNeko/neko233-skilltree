package com.neko233.skilltree.commons.core.compress.punch_date;

import com.neko233.skilltree.commons.core.base.StringUtils233;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 打卡日期压缩记录.
 * 将 10000 行记录, 压缩成一行. disk -> cpu
 *
 * @author SolarisNeko on 2023-02-18
 **/
public class PunchDateRecord233 {


    public static final ZoneOffset ZONE_OFFSET_CHINA = ZoneOffset.of("+8");
    BitSet loginBitSet;

    /**
     * default from 1970-01-01 00:00:00 开始
     */
    private static final LocalDateTime DEFAULT_ALL_START_DATETIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private final LocalDateTime allStartDateTime = DEFAULT_ALL_START_DATETIME;

    /**
     * default 1024 year
     */
    public PunchDateRecord233() {
        this(new BitSet(366 * 1024));
    }

    public PunchDateRecord233(BitSet bitSet) {
        assert bitSet != null;
        assert allStartDateTime != null;
        this.loginBitSet = bitSet;
    }

    public int size() {
        return loginBitSet.size();
    }

    /**
     * 打卡日期
     *
     * @param dateTimeStr 日期时间 yyyy-MM-dd HH:mm:ss
     */
    public void punchDateByDateTimeString(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TemporalAccessor parse = dateTimeFormatter.parse(dateTimeStr);
        LocalDateTime from = LocalDateTime.from(parse);
        punchDateByJdkDateTime(from);
    }

    public void punchDateByJdkDateTime(LocalDateTime from) {
        long targetSecond = from.toEpochSecond(ZONE_OFFSET_CHINA);
        punchDateByTimeMs(targetSecond * 1000);
    }

    /**
     * 已经过了多少日, 进行打卡
     *
     * @param offsetDaysFrom_1970_01_01 偏移了多少日
     */
    public void punchDateByOffsetDays(int offsetDaysFrom_1970_01_01) {
        loginBitSet.set(offsetDaysFrom_1970_01_01);
    }

    /**
     * @param currentMs 当前时间戳
     */
    public void punchDateByTimeMs(long currentMs) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(currentMs / 1000L, 0, ZONE_OFFSET_CHINA);
        Duration between = Duration.between(allStartDateTime, localDateTime);
        long offsetLoginDay = between.toDays();
        loginBitSet.set((int) offsetLoginDay);
    }


    /**
     * get all 打卡日期 string
     *
     * @return punch datetime string
     */
    public List<String> getAllPunchDateTimeString() {
        final List<Integer> loginDays = new ArrayList<>();
        int index = 0;
        while (true) {
            int setIndex = loginBitSet.nextSetBit(index);
            if (setIndex == -1) {
                break;
            }
            index = setIndex + 1;
            loginDays.add(setIndex);
        }
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return loginDays.stream()
                .map(allStartDateTime::plusDays)
                .map(dtFormatter::format)
                .collect(Collectors.toList());
    }

    /**
     * @return cardinality
     */
    public int cardinality() {
        return loginBitSet.cardinality();
    }

    /**
     * decode
     *
     * @param serializeBytes 二进制
     * @return PunchDateRecord233
     */
    public static PunchDateRecord233 from(byte[] serializeBytes) {
        assert serializeBytes != null;
        BitSet bitSet = BitSet.valueOf(serializeBytes);
        return new PunchDateRecord233(bitSet);
    }

    /**
     * decode
     *
     * @param serializeString UTF-8 编码后的二进制
     * @return PunchDateRecord233
     */
    public static PunchDateRecord233 from(String serializeString) {
        if (StringUtils233.isBlank(serializeString)) {
            throw new IllegalArgumentException("serialize string is blank!");
        }
        byte[] bytes = serializeString.getBytes(StandardCharsets.UTF_8);
        BitSet bitSet = BitSet.valueOf(bytes);
        return new PunchDateRecord233(bitSet);
    }

    /**
     * code
     *
     * @return 序列化
     */
    public byte[] getSerializeByes() {
        return loginBitSet.toByteArray();
    }


    public String getSerializeString() {
        byte[] serializeByes = getSerializeByes();
        return new String(serializeByes, StandardCharsets.UTF_8);
    }
}
