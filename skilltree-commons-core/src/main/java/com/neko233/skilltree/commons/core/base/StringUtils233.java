package com.neko233.skilltree.commons.core.base;


import com.neko233.skilltree.commons.core.annotation.NotNull;
import com.neko233.skilltree.commons.core.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.WARNING;

public class StringUtils233 {


    /**
     * 没找到的 index
     */
    public static final int INDEX_NOT_FOUND = -1;
    /**
     * char 空格
     */
    public static final char C_SPACE = ' ';
    /**
     * 空格
     */
    public static final String SPACE = " ";
    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    /**
     * A String for linefeed LF ("\n").
     * 换行
     */
    public static final String LF = "\n";
    /**
     * carriage return CR ("\r").
     * 回车
     */
    public static final String CR = "\r";
    /**
     * 空数组
     */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * 最大处理字符串
     */
    private static final int MAX_HANDLE_STRING = 8192;
    /**
     * 字符常量：斜杠 {@code '/'}
     */
    private static final char C_SLASH = '/';
    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    private static final char C_BACKSLASH = '\\';
    /**
     * 字符串常量：空 JSON {@code "{}"}
     */
    private static final String EMPTY_JSON = "{}";


    /**
     * 安全 trim
     *
     * @param input 输入
     * @return 内容
     */
    @NotNull
    public static String trim(final String input) {
        if (StringUtils233.isBlank(input)) {
            return "";
        }
        return input.trim();
    }

    public static boolean isEmpty(final char... cs) {
        return isEmpty(new String(cs));
    }

    /**
     * 空字符串判断
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @since 0.1.8
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * 首字母大写
     *
     * @param content 文本
     * @return aaa -> Aaa
     */
    public static String firstWordUpperCase(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toUpperCase();
        }
        return content.substring(0, 1).toUpperCase() + content.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param content 文本
     * @return AAa -> aAa
     */
    public static String firstWordLowerCase(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (content.length() == 1) {
            return content.toLowerCase();
        }
        return content.substring(0, 1).toLowerCase() + content.substring(1);
    }


    /**
     * 转换成 Big Camel（大驼峰）的 Upper Case 版本!
     *
     * @return SystemUser -> SYSTEM_USER
     */
    public static String toBigCamelCaseUpper(String name) {
        StringBuilder builder = new StringBuilder();
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            return name;
        }

        // 首字母不处理
        builder.append(chars[0]);
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            final boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // is Upper Case
                builder.append("_").append(aChar);
            } else {
                // is Lower Case
                final char upperChar = Character.toUpperCase(aChar);
                builder.append(upperChar);
            }
        }
        return builder.toString();
    }

    /**
     * 转换成 Big Camel（大驼峰）的 lower case 版本!
     *
     * @return SystemUser -> system_user
     */
    public static String toBigCamelCaseLower(String name) {
        StringBuilder builder = new StringBuilder();
        // 转化成 char[] 流
        char[] chars = name.toCharArray();
        if (chars.length == 0) {
            return name;
        }
        // 首字母不处理
        builder.append(Character.toLowerCase(chars[0]));
        // [1, n] 字母, 遇到大写, 进行大驼峰处理
        for (int index = 1; index < chars.length; index++) {
            char aChar = chars[index];
            boolean upperCase = Character.isUpperCase(aChar);
            if (upperCase) {
                // Upper Case
                builder.append("_")
                        .append(Character.toLowerCase(aChar));
            } else {
                // Lower Case
                char upperChar = Character.toLowerCase(aChar);
                builder.append(upperChar);
            }
        }
        return builder.toString();
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String truncate(final String str,
                                  final int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    public static String truncate(final String str,
                                  final int offset,
                                  final int maxWidth) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth) {
            final int ix = Math.min(offset + maxWidth, str.length());
            return str.substring(offset, ix);
        }
        return str.substring(offset);
    }

    /**
     * not null upper case!
     */
    public static String upperCase(final String str) {
        return upperCase(str, null);
    }

    public static String upperCase(final String str,
                                   final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toUpperCase();
        }
        return str.toUpperCase(locale);
    }

    public static String lowerCase(final String str) {
        return lowerCase(str, null);
    }

    public static String lowerCase(final String str,
                                   final Locale locale) {
        if (str == null) {
            return null;
        }
        if (locale == null) {
            return str.toLowerCase();
        }
        return str.toLowerCase(locale);
    }


    /**
     * find some data between [openStr, closeStr]
     * maybe null
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param targetString the String containing the substring, may be null
     * @param openStr      the String before the substring, may be null
     * @param closeStr     the String after the substring, may be null
     * @return the substring, {@code null} if no match
     * @since 2.0
     */
    public static String substringBetween(final String targetString,
                                          final String openStr,
                                          final String closeStr) {
        if (ObjectUtils233.isAnyNull(targetString, openStr, closeStr)) {
            return null;
        }
        final int start = targetString.indexOf(openStr);
        if (start != INDEX_NOT_FOUND) {
            final int end = targetString.indexOf(closeStr, start + openStr.length());
            if (end != INDEX_NOT_FOUND) {
                return targetString.substring(start + openStr.length(), end);
            }
        }
        return null;
    }


    /**
     * "1, 2, 3" --> "1,2,3"
     *
     * @param s         文本
     * @param separator 分隔符
     * @return trim 后的内容
     */
    public static String splitTrimThenJoin(String s,
                                           String separator) {
        String notNullStr = Optional.of(s).orElse("");
        return Arrays.stream(notNullStr.split(separator)).map(String::trim).filter(StringUtils233::isNotBlank).collect(Collectors.joining(separator));
    }

    /**
     * JDK 默认的 join 不方便
     */
    public static String join(CharSequence delimiter,
                              Object... elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        // Number of elements not likely worth Arrays.stream overhead.
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object obj : elements) {
            joiner.add(String.valueOf(obj));
        }
        return joiner.toString();
    }


    public static String[] tokenizeToStringArray(@Nullable String str,
                                                 String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * split & trim & ignore null token
     *
     * @param str               内容
     * @param delimiters        分隔符
     * @param trimTokens        trim ?
     * @param ignoreEmptyTokens ignore empty?
     * @return String
     */
    public static String[] tokenizeToStringArray(@Nullable String str,
                                                 String delimiters,
                                                 boolean trimTokens,
                                                 boolean ignoreEmptyTokens) {
        if (str == null) {
            return new String[]{};
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            List<String> tokens = new ArrayList<>();

            while (true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return toStringArray(tokens);
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while (ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return CollectionUtils.isNotEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

    public static void appendByPrintStyle(StringBuilder builder,
                                          String key,
                                          Object value) {
        builder.append(key).append(value).append("\n");
    }


    public static String format(String strPattern,
                                Object... argArray) {
        return template(strPattern, "{}", argArray);
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将指定占位符 按照顺序替换为参数<br>
     * 如果想输出占位符使用 \\转义即可，如果想输出占位符之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "{}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "{}", "a", "b") =》 this is {} for a<br>
     * 转义\： format("this is \\\\{} for {}", "{}", "a", "b") =》 this is \a for b<br>
     *
     * @param pattern     字符串模板
     * @param placeHolder 占位符，例如{}
     * @param argArray    参数列表
     * @return 结果
     */
    public static String template(String pattern,
                                  String placeHolder,
                                  Object... argArray) {
        if (StringUtils233.isBlank(pattern) || StringUtils233.isBlank(placeHolder) || ArrayUtils233.isEmpty(argArray)) {
            return pattern;
        }
        final int strPatternLength = pattern.length();
        final int placeHolderLength = placeHolder.length();

        // 初始化定义好的长度以获得更好的性能
        final StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = pattern.indexOf(placeHolder, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return pattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(pattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && pattern.charAt(delimIndex - 1) == StringUtils233.C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && pattern.charAt(delimIndex - 2) == StringUtils233.C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(pattern, handledPosition, delimIndex - 1);
                    sbuf.append(String.valueOf(argArray[argIndex]));
                    handledPosition = delimIndex + placeHolderLength;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(pattern, handledPosition, delimIndex - 1);
                    sbuf.append(placeHolder.charAt(0));
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                sbuf.append(pattern, handledPosition, delimIndex);
                sbuf.append(StringUtils233.toUtf8String(argArray[argIndex]));
                handledPosition = delimIndex + placeHolderLength;
            }
        }

        // 加入最后一个占位符后所有的字符
        sbuf.append(pattern, handledPosition, strPatternLength);

        return sbuf.toString();
    }

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toUtf8String(Object obj) {
        return stringIfNull(obj, StandardCharsets.UTF_8);
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String stringIfNull(Object obj,
                                      Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return stringIfNull((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return stringIfNull((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return stringIfNull((ByteBuffer) obj, charset);
        } else if (ArrayUtils233.isArray(obj)) {
            return ArrayUtils233.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     * @since 4.2.2
     */
    public static String removeAll(CharSequence str,
                                   char... chars) {
        if (str == null) {
            return null;
        }
        if (isEmpty(chars)) {
            return str.toString();
        }
        final int len = str.length();
        if (0 == len) {
            return str.toString();
        }
        final StringBuilder builder = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (containsChar(chars, c)) {
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 数组中是否包含元素
     *
     * @param chars 字符数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    private static boolean containsChar(char[] chars,
                                        char value) {
        if (null != chars) {
            for (int i = 0; i < chars.length; i++) {
                if (value == chars[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> splitTrim(String str,
                                         String split) {
        if (str == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(split)).map(String::trim).collect(Collectors.toList());
    }

    /**
     * public static String subPre(String startIp, int lastDotIndex) {
     * 切割指定位置之前部分的字符串
     *
     * @param string      字符串
     * @param subPreIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     */
    public static String subPre(CharSequence string,
                                int subPreIndex) {
        return sub(string, 0, subPreIndex);
    }


    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str              String
     * @param fromIndexInclude 开始的index（包括）
     * @param toIndexExclude   结束的index（不包括）
     * @return 字串
     */
    public static String sub(CharSequence str,
                             int fromIndexInclude,
                             int toIndexExclude) {
        if (isEmpty(str)) {
            return stringIfNull(str);
        }
        int len = str.length();

        if (fromIndexInclude < 0) {
            fromIndexInclude = len + fromIndexInclude;
            if (fromIndexInclude < 0) {
                fromIndexInclude = 0;
            }
        } else if (fromIndexInclude > len) {
            fromIndexInclude = len;
        }

        if (toIndexExclude < 0) {
            toIndexExclude = len + toIndexExclude;
            if (toIndexExclude < 0) {
                toIndexExclude = len;
            }
        } else if (toIndexExclude > len) {
            toIndexExclude = len;
        }

        if (toIndexExclude < fromIndexInclude) {
            int tmp = fromIndexInclude;
            fromIndexInclude = toIndexExclude;
            toIndexExclude = tmp;
        }

        if (fromIndexInclude == toIndexExclude) {
            return EMPTY;
        }

        return str.toString().substring(fromIndexInclude, toIndexExclude);
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String stringIfNull(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 切割指定位置之后部分的字符串
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     */
    public static String subSuf(CharSequence string,
                                int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 替换指定字符串的指定区间内字符为"*"
     * 俗称：脱敏功能，后面其他功能，可以见：DesensitizedUtil(脱敏工具类)
     *
     * <pre>
     * StrUtil.hide(null,*,*)=null
     * StrUtil.hide("",0,*)=""
     * StrUtil.hide("jackduan@163.com",-1,4)   ****duan@163.com
     * StrUtil.hide("jackduan@163.com",2,3)    ja*kduan@163.com
     * StrUtil.hide("jackduan@163.com",3,2)    jackduan@163.com
     * StrUtil.hide("jackduan@163.com",16,16)  jackduan@163.com
     * StrUtil.hide("jackduan@163.com",16,17)  jackduan@163.com
     * </pre>
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @return 替换后的字符串
     * @since 4.1.14
     */
    public static String hide(CharSequence str,
                              int startInclude,
                              int endExclude) {
        return replaceEx(str, startInclude, endExclude, '*');
    }

    /**
     * 替换指定字符串的指定区间内字符为固定字符<br>
     * 此方法使用{@link String#codePoints()}完成拆分替换
     *
     * @param str          字符串
     * @param startInclude 开始位置（包含）
     * @param endExclude   结束位置（不包含）
     * @param replacedChar 被替换的字符
     * @return 替换后的字符串
     * @since 3.2.1
     */
    public static String replaceEx(CharSequence str,
                                   int startInclude,
                                   int endExclude,
                                   char replacedChar) {
        if (isEmpty(str)) {
            return stringIfNull(str);
        }
        final String originalStr = stringIfNull(str);
        int[] strCodePoints = originalStr.codePoints().toArray();
        final int strLength = strCodePoints.length;
        if (startInclude > strLength) {
            return originalStr;
        }
        if (endExclude > strLength) {
            endExclude = strLength;
        }
        if (startInclude > endExclude) {
            // 如果起始位置大于结束位置，不替换
            return originalStr;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            if (i >= startInclude && i < endExclude) {
                stringBuilder.append(replacedChar);
            } else {
                stringBuilder.append(new String(strCodePoints, i, 1));
            }
        }
        return stringBuilder.toString();
    }


    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }

    /**
     * string is a number string ?
     *
     * @param str 文本
     * @return is number ?
     */
    public static boolean isNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        int start = (str.charAt(0) == '-') ? 1 : 0;
        if (sz > start + 1) {
            if (str.charAt(start) == '0' && str.charAt(start + 1) == 'x') {
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    if ((ch < '0' || ch > '9')
                            && (ch < 'a' || ch > 'f')
                            && (ch < 'A' || ch > 'F')) {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (ch == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (ch == 'e' || ch == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (ch == '+' || ch == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < str.length()) {
            char ch = str.charAt(i);

            if (ch >= '0' && ch <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (ch == 'e' || ch == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (!allowSigns
                    && (ch == 'd'
                    || ch == 'D'
                    || ch == 'f'
                    || ch == 'F')) {
                return foundDigit;
            }
            if (ch == 'l'
                    || ch == 'L') {
                // not allowing L with an exponent
                return foundDigit && !hasExp;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * \"abc\" -> abc
     *
     * @param stringText 文本, \" will delete | 如果还要表示文本, 使用 'abc'
     * @return just a string object
     */
    public static String stringTextToStringObject(String stringText) {
        return Optional.ofNullable(stringText).orElse("")
                .replaceAll("\"", "");
    }


    /**
     * 每隔多少步, 加入一些
     *
     * @param string
     * @param step
     * @return
     */
    public static String joinByStep(String string,
                                    Integer step,
                                    String joiner) {
        if (StringUtils233.isBlank(string)) {
            return "";
        }
        if (step <= 0) {
            return string;
        }
        StringBuilder concatBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i = i + step) {
            concatBuilder.append(string, i, i + step).append(joiner);
            if (i + step >= string.length()) {
                concatBuilder.delete(concatBuilder.length() - 1, concatBuilder.length());
            }
        }
        return concatBuilder.toString();
    }

    public static String lenientFormat(
            @NotNull String template,
            @Nullable Object... args) {
        template = String.valueOf(template); // null -> "null"

        if (args == null) {
            args = new Object[]{"(Object[])null"};
        } else {
            for (int i = 0; i < args.length; i++) {
                args[i] = lenientToString(args[i]);
            }
        }

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template, templateStart, placeholderStart);
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template, templateStart, template.length());

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }


    private static String lenientToString(@NotNull Object o) {
        if (o == null) {
            return "null";
        }
        try {
            return o.toString();
        } catch (Exception e) {
            // Default toString() behavior - see Object.toString()
            String objectToString =
                    o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));
            // Logger is created inline with fixed name to avoid forcing Proguard to create another class.
            Logger.getLogger("com.google.common.base.Strings")
                    .log(WARNING, "Exception during lenientFormat for " + objectToString, e);
            return "<" + objectToString + " threw " + e.getClass().getName() + ">";
        }
    }
}
