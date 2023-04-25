package com.neko233.toolchain.commons.core.base;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * 优先以 java-8 的 java.time 为标准, 该日期工具主要做补充 & 方便
 *
 * @author SolarisNeko
 */
public class DateTimeUtils233 {

    /**
     * 是否闰年
     *
     * @param year 年
     * @return 是否闰年. 2月 29 日
     */
    public static boolean isLeapYear(int year) {
        return Year.isLeap(year);
    }


    /**
     * @return 当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 解析时间戳 text
     *
     * @param datetimeText 文本
     * @param pattern      格式
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String datetimeText,
                                      String pattern) {
        if (StringUtils233.isBlank(datetimeText)) {
            throw new IllegalArgumentException("your dateTime is blank!");
        }
        if (StringUtils233.isBlank(pattern)) {
            throw new IllegalArgumentException("your pattern is blank!");
        }
        List<String> strings = StringUtils233.splitTrim(pattern, " ");
        DateTimeFormatter dtPattern = DateTimeFormatter.ofPattern(pattern);
        if (strings.size() == 1) {
            if (pattern.contains("HH")) {
                return LocalTime.parse(datetimeText, dtPattern).atDate(LocalDate.now());
            }
            if (pattern.contains("yyyy")) {
                return LocalDate.parse(datetimeText, dtPattern).atStartOfDay();
            }
        }


        List<String> dateTimeStr = StringUtils233.splitTrim(datetimeText, " ");
        if (dateTimeStr.size() == 1) {
            return LocalDateTime.parse(datetimeText + " 00:00:00", dtPattern);
        }
        return LocalDateTime.parse(datetimeText, dtPattern);
    }


    /**
     * DateTime -> String
     *
     * @param dateTime 日期
     * @param pattern  格式
     * @return datetime String
     */
    public static String toString(LocalDateTime dateTime,
                                  String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 获取毫秒数, 当前时区
     *
     * @param dateTime 给定时间
     * @return 日期
     */
    public static long getMs(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(OffsetDateTime.now().getOffset()) * 1000;
    }

    /**
     * 获取毫秒数, 当前时区
     *
     * @param dateTime   给定时间
     * @param zoneOffset 时区偏移
     * @return 日期
     */
    public static long getMs(LocalDateTime dateTime,
                             ZoneOffset zoneOffset) {
        return dateTime.toEpochSecond(zoneOffset) * 1000;
    }


    public static long getMs(String dateTimeStr,
                             String format) {
        return DateTimeUtils233.parse(dateTimeStr, format).toEpochSecond(ZoneOffset.of(ZoneOffset.UTC.getId())) * 1000;
    }

    public static long getMs(String dateTime,
                             String format,
                             ZoneOffset zoneOffset) {
        return DateTimeUtils233.parse(dateTime, format).toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthdayDt 生日
     * @param otherDt    需要对比的日期
     * @return 年龄
     */
    public static int age(LocalDateTime birthdayDt,
                          LocalDateTime otherDt) {
        AssertUtils233.isNotNull(birthdayDt, "Birthday can not be null !");
        if (otherDt == null) {
            otherDt = now();
        }
        return age(getMs(birthdayDt), getMs(otherDt));
    }


    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthdayMs 生日
     * @param otherMs    需要对比的日期
     * @return 年龄
     */
    protected static int age(long birthdayMs,
                             long otherMs) {
        if (birthdayMs > otherMs) {
            throw new IllegalArgumentException("Birthday is after dateToCompare!");
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(otherMs);

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final boolean isLastDayOfMonth = dayOfMonth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.setTimeInMillis(birthdayMs);
        int age = year - cal.get(Calendar.YEAR);

        final int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {

            final int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            final boolean isLastDayOfMonthBirth = dayOfMonthBirth == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            if ((false == isLastDayOfMonth || false == isLastDayOfMonthBirth) && dayOfMonth < dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }


}
