package com.neko233.toolchain.commons.core.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberUtils233 {


    public static long addLongIdByLoop(long currentSequenceId, long beginNumber, long endNumber) {
        long temp = currentSequenceId + 1;
        // loop
        temp = temp > endNumber ? beginNumber : temp;
        return temp;
    }


    public static long getLongIdByLoop(long currentSequenceId, long beginNumber, long endNumber) {
        long diff = endNumber - beginNumber;
        long temp = currentSequenceId;
        // loop
        temp = temp < beginNumber ?  endNumber - Math.abs(temp) % diff + 1 : temp;
        temp = temp > endNumber ? beginNumber : temp;
        return temp;
    }



    public static BigDecimal round(Number number, int scale, RoundingMode roundingMode) {
        return round(number.doubleValue(), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }


    /**
     * 数字转{@link BigDecimal}<br>
     * Float、Double等有精度问题，转换为字符串后再转换<br>
     * null转换为0
     */
    public static BigDecimal toBigDecimal(Number number) {
        if (null == number) {
            return BigDecimal.ZERO;
        }

        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Long) {
            return new BigDecimal((Long) number);
        } else if (number instanceof Integer) {
            return new BigDecimal((Integer) number);
        } else if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }

        // Float、Double等有精度问题，转换为字符串后再转换
        return toBigDecimal(number.toString());
    }

    /**
     * 数字转{@link BigDecimal}<br>
     * null或""或空白符转换为0
     *
     * @param numberStr 数字字符串
     * @return {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(String numberStr) {
        if (StringUtils233.isBlank(numberStr)) {
            return BigDecimal.ZERO;
        }

        try {
            // 支持类似于 1,234.55 格式的数字
            final Number number = parseNumber(numberStr);
            if (number instanceof BigDecimal) {
                return (BigDecimal) number;
            } else {
                return new BigDecimal(number.toString());
            }
        } catch (Exception ignore) {
            // 忽略解析错误
        }

        return new BigDecimal(numberStr);
    }

    /**
     * 将指定字符串转换为{@link Number} 对象<br>
     * 此方法不支持科学计数法
     *
     * @param numberStr Number字符串
     * @return Number对象
     * @throws NumberFormatException 包装了{@link ParseException}，当给定的数字字符串无法解析时抛出
     */
    public static Number parseNumber(String numberStr) throws NumberFormatException {
        if (numberStr.startsWith("0x")) {
            // 0x04表示16进制数
            return Long.parseLong(numberStr.substring(2), 16);
        }

        try {
            final NumberFormat format = NumberFormat.getInstance();
            if (format instanceof DecimalFormat) {
                // 当字符串数字超出double的长度时，会导致截断，此处使用BigDecimal接收
                ((DecimalFormat) format).setParseBigDecimal(true);
            }
            return format.parse(numberStr);
        } catch (ParseException e) {
            final NumberFormatException nfe = new NumberFormatException(e.getMessage());
            nfe.initCause(e);
            throw nfe;
        }
    }

}
