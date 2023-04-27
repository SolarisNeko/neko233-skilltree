package com.neko233.skilltree.commons.core.base;


import com.neko233.skilltree.commons.core.cache.strings.ASCIIStrCache;
import com.neko233.skilltree.commons.core.pool.CharPool;

/**
 * char 工具类<br>
 * 因为 Java 的 char 和 String 割裂太强, 故单独一个工具
 *
 * @author from network
 */
public class CharUtils233 implements CharPool {

    /**
     * 是否为ASCII字符，ASCII字符位于0~127之间
     *
     * <pre>
     *   CharUtils233.isAscii('a')  = true
     *   CharUtils233.isAscii('A')  = true
     *   CharUtils233.isAscii('3')  = true
     *   CharUtils233.isAscii('-')  = true
     *   CharUtils233.isAscii('\n') = true
     *   CharUtils233.isAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return true表示为ASCII字符，ASCII字符位于0~127之间
     */
    public static boolean isAscii(char ch) {
        return ch < 128;
    }

    /**
     * 是否为可见ASCII字符，可见字符位于32~126之间
     *
     * <pre>
     *   CharUtils233.isAsciiPrintable('a')  = true
     *   CharUtils233.isAsciiPrintable('A')  = true
     *   CharUtils233.isAsciiPrintable('3')  = true
     *   CharUtils233.isAsciiPrintable('-')  = true
     *   CharUtils233.isAsciiPrintable('\n') = false
     *   CharUtils233.isAsciiPrintable('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return true表示为ASCII可见字符，可见字符位于32~126之间
     */
    public static boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * 是否为ASCII控制符（不可见字符），控制符位于0~31和127
     *
     * <pre>
     *   CharUtils233.isAsciiControl('a')  = false
     *   CharUtils233.isAsciiControl('A')  = false
     *   CharUtils233.isAsciiControl('3')  = false
     *   CharUtils233.isAsciiControl('-')  = false
     *   CharUtils233.isAsciiControl('\n') = true
     *   CharUtils233.isAsciiControl('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为控制符，控制符位于0~31和127
     */
    public static boolean isAsciiControl(final char ch) {
        return ch < 32 || ch == 127;
    }

    /**
     * 判断是否为字母（包括大写字母和小写字母）<br>
     * 字母包括A~Z和a~z
     *
     * <pre>
     *   CharUtils233.isLetter('a')  = true
     *   CharUtils233.isLetter('A')  = true
     *   CharUtils233.isLetter('3')  = false
     *   CharUtils233.isLetter('-')  = false
     *   CharUtils233.isLetter('\n') = false
     *   CharUtils233.isLetter('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为字母（包括大写字母和小写字母）字母包括A~Z和a~z
     */
    public static boolean isLetter(char ch) {
        return isLetterUpper(ch) || isLetterLower(ch);
    }

    /**
     * <p>
     * 判断是否为大写字母，大写字母包括A~Z
     * </p>
     *
     * <pre>
     *   CharUtils233.isLetterUpper('a')  = false
     *   CharUtils233.isLetterUpper('A')  = true
     *   CharUtils233.isLetterUpper('3')  = false
     *   CharUtils233.isLetterUpper('-')  = false
     *   CharUtils233.isLetterUpper('\n') = false
     *   CharUtils233.isLetterUpper('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为大写字母，大写字母包括A~Z
     */
    public static boolean isLetterUpper(final char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    /**
     * <p>
     * 检查字符是否为小写字母，小写字母指a~z
     * </p>
     *
     * <pre>
     *   CharUtils233.isLetterLower('a')  = true
     *   CharUtils233.isLetterLower('A')  = false
     *   CharUtils233.isLetterLower('3')  = false
     *   CharUtils233.isLetterLower('-')  = false
     *   CharUtils233.isLetterLower('\n') = false
     *   CharUtils233.isLetterLower('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为小写字母，小写字母指a~z
     */
    public static boolean isLetterLower(final char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    /**
     * <p>
     * 检查是否为数字字符，数字字符指0~9
     * </p>
     *
     * <pre>
     *   CharUtils233.isNumber('a')  = false
     *   CharUtils233.isNumber('A')  = false
     *   CharUtils233.isNumber('3')  = true
     *   CharUtils233.isNumber('-')  = false
     *   CharUtils233.isNumber('\n') = false
     *   CharUtils233.isNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为数字字符，数字字符指0~9
     */
    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * 是否为16进制规范的字符，判断是否为如下字符
     * <pre>
     * 1. 0~9
     * 2. a~f
     * 4. A~F
     * </pre>
     *
     * @param c 字符
     * @return 是否为16进制规范的字符
     * @since 4.1.5
     */
    public static boolean isHexChar(char c) {
        return isNumber(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    /**
     * 是否为字母或数字，包括A~Z、a~z、0~9
     *
     * <pre>
     *   CharUtils233.isLetterOrNumber('a')  = true
     *   CharUtils233.isLetterOrNumber('A')  = true
     *   CharUtils233.isLetterOrNumber('3')  = true
     *   CharUtils233.isLetterOrNumber('-')  = false
     *   CharUtils233.isLetterOrNumber('\n') = false
     *   CharUtils233.isLetterOrNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为字母或数字，包括A~Z、a~z、0~9
     */
    public static boolean isLetterOrNumber(final char ch) {
        return isLetter(ch) || isNumber(ch);
    }

    /**
     * 字符转为字符串<br>
     * 如果为ASCII字符，使用缓存
     *
     * @param c 字符
     * @return 字符串
     * @see ASCIIStrCache#toString(char)
     */
    public static String toString(char c) {
        return ASCIIStrCache.toString(c);
    }

    /**
     * 给定类名是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param clazz 被检查的类
     * @return true表示为字符类
     */
    public static boolean isCharClass(Class<?> clazz) {
        return clazz == Character.class || clazz == char.class;
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param value 被检查的对象
     * @return true表示为字符类
     */
    public static boolean isChar(Object value) {
        //noinspection ConstantConditions
        return value instanceof Character || value.getClass() == char.class;
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000';
    }

    /**
     * 判断是否为emoji表情符<br>
     *
     * @param c 字符
     * @return 是否为emoji
     * @since 4.0.8
     */
    public static boolean isEmoji(char c) {
        //noinspection ConstantConditions
        return false == ((c == 0x0) || //
                (c == 0x9) || //
                (c == 0xA) || //
                (c == 0xD) || //
                ((c >= 0x20) && (c <= 0xD7FF)) || //
                ((c >= 0xE000) && (c <= 0xFFFD)) || //
                ((c >= 0x100000) && (c <= 0x10FFFF)));
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     * @since 4.1.11
     */
    public static boolean isFileSeparator(char c) {
        return SLASH == c || BACKSLASH == c;
    }

    /**
     * 比较两个字符是否相同
     *
     * @param c1              字符1
     * @param c2              字符2
     * @param caseInsensitive 是否忽略大小写
     * @return 是否相同
     * @since 4.0.3
     */
    public static boolean equals(char c1, char c2, boolean caseInsensitive) {
        if (caseInsensitive) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        }
        return c1 == c2;
    }

    /**
     * 获取字符类型
     *
     * @param c 字符
     * @return 字符类型
     * @since 5.2.3
     */
    public static int getType(int c) {
        return Character.getType(c);
    }

    /**
     * 获取给定字符的16进制数值
     *
     * @param b 字符
     * @return 16进制字符
     * @since 5.3.1
     */
    public static int digit16(int b) {
        return Character.digit(b, 16);
    }

    /**
     * 将字母、数字转换为带圈的字符：
     * <pre>
     *     '1' -》 '①'
     *     'A' -》 'Ⓐ'
     *     'a' -》 'ⓐ'
     * </pre>
     * <p>
     * 获取带圈数字 /封闭式字母数字 ，从1-20,超过1-20报错
     *
     * @param c 被转换的字符，如果字符不支持转换，返回原字符
     * @return 转换后的字符
     * @see <a href="https://en.wikipedia.org/wiki/List_of_Unicode_characters#Unicode_symbols">Unicode_symbols</a>
     * @see <a href="https://en.wikipedia.org/wiki/Enclosed_Alphanumerics">Alphanumerics</a>
     * @since 5.6.2
     */
    public static char toCloseChar(char c) {
        int result = c;
        if (c >= '1' && c <= '9') {
            result = '①' + c - '1';
        } else if (c >= 'A' && c <= 'Z') {
            result = 'Ⓐ' + c - 'A';
        } else if (c >= 'a' && c <= 'z') {
            result = 'ⓐ' + c - 'a';
        }
        return (char) result;
    }

    /**
     * 将[1-20]数字转换为带圈的字符：
     * <pre>
     *     1 -》 '①'
     *     12 -》 '⑫'
     *     20 -》 '⑳'
     * </pre>
     * 也称作：封闭式字符，英文：Enclosed Alphanumerics
     *
     * @param number 被转换的数字
     * @return 转换后的字符
     * @author dazer
     * @see <a href="https://en.wikipedia.org/wiki/List_of_Unicode_characters#Unicode_symbols">维基百科wikipedia-Unicode_symbols</a>
     * @see <a href="https://zh.wikipedia.org/wiki/Unicode%E5%AD%97%E7%AC%A6%E5%88%97%E8%A1%A8">维基百科wikipedia-Unicode字符列表</a>
     * @see <a href="https://coolsymbol.com/">coolsymbol</a>
     * @see <a href="https://baike.baidu.com/item/%E7%89%B9%E6%AE%8A%E5%AD%97%E7%AC%A6/112715?fr=aladdin">百度百科 特殊字符</a>
     * @since 5.6.2
     */
    public static char toCloseByNumber(int number) {
        if (number > 20) {
            throw new IllegalArgumentException("Number must be [1-20]");
        }
        return (char) ('①' + number - 1);
    }
}
