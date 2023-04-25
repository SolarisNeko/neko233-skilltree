package com.neko233.toolchain.commons.core.base;

import com.neko233.toolchain.commons.core.dataStruct.maps.BiDirectionalMap;
import com.neko233.toolchain.commons.core.pool.DataSizePool;

import java.math.BigDecimal;

public class DataSizeUtils233 {

    public static final Long COMPUTER_SIZE = 1024L;
    public static final BigDecimal MAX_COMPUTE_SIZE = BigDecimal.valueOf(1024);


    // level 作为次方值
    private static final BiDirectionalMap<Integer, String> levelToSizeMap = new BiDirectionalMap<Integer, String>() {{
        put(1, DataSizePool.Byte);
        put(2, DataSizePool.KB);
        put(3, DataSizePool.MB);
        put(4, DataSizePool.GB);
        put(5, DataSizePool.TB);
        put(6, DataSizePool.PB);
        put(7, DataSizePool.EB);
        put(8, DataSizePool.ZB);
        put(9, DataSizePool.YB);
        put(10, DataSizePool.BB);
        put(11, DataSizePool.NB);
        put(12, DataSizePool.DB);
        put(13, DataSizePool.CB);
    }};


    /**
     * 格式化数值
     *
     * @param byteNumber 字节数大小
     * @return 格式化内容
     */
    public static String toHumanFormatByByte(BigDecimal byteNumber) {
        return toHumanFormatByByte(byteNumber.longValue());
    }

    public static String toHumanFormatByByte(long byteNumber) {
        BigDecimal byteNum = BigDecimal.valueOf(byteNumber);
        int level = 1;
        for (int i = 1; i < levelToSizeMap.size(); i++) {
            // next test
            BigDecimal power = MathUtils233.power(COMPUTER_SIZE, i);
            BigDecimal value = MathUtils233.divide(byteNum, power);
            if (value.compareTo(BigDecimal.ONE) > 0) {
                level += 1;
            }
            if (value.compareTo(MAX_COMPUTE_SIZE) < 0) {
                return value + StringUtils233.SPACE + levelToSizeMap.getForward(level);
            }
            continue;
        }
        return Constants233.UNKNOWN;
    }

    public static BigDecimal calculateDataSize(long number, String dataSizeUnit) {
        Integer level = levelToSizeMap.getBack(dataSizeUnit);
        // 因为是正向计算, 次方 - 1
        BigDecimal power = MathUtils233.power(COMPUTER_SIZE, level - 1);
        return MathUtils233.multiply(number, power);
    }


}
