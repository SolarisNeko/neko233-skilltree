package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.commons.core.regexPattern.MatcherApi;
import com.neko233.skilltree.commons.core.regexPattern.PatternPool;
import com.neko233.skilltree.commons.core.regexPattern.RegexPool;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils233 {

    /**
     * 正则表达式匹配中文汉字
     */
    public final static String RE_CHINESE = RegexPool.CHINESE;

    /**
     * 正则表达式匹配中文字符串
     */
    public final static String RE_CHINESES = RegexPool.CHINESES;

    /**
     * 正则中需要被转义的关键字
     */
    public final static Set<Character> RE_KEYS = CollectionUtils233.newHashSet('$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|');


    /**
     * 给定内容是否匹配正则
     *
     * @param regex   正则
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(String regex, CharSequence content) {
        if (content == null) {
            // 提供null的字符串为不匹配
            return false;
        }

        if (StringUtils233.isEmpty(regex)) {
            // 正则不存在则为全匹配
            return true;
        }

        final Pattern pattern = PatternPool.get(regex, Pattern.DOTALL);
        return isMatch(pattern, content);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }


    /**
     * 字符串的每一个字符是否都与定义的匹配器匹配
     *
     * @param value   字符串
     * @param matcher 匹配器
     * @return 是否全部匹配
     * @since 3.2.3
     */
    public static boolean isAllCharMatch(CharSequence value, MatcherApi<Character> matcher) {
        if (StringUtils233.isBlank(value)) {
            return false;
        }
        for (int i = value.length(); --i >= 0; ) {
            if (!matcher.match(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 指定内容中是否有表达式匹配的内容
     *
     * @param regex   正则表达式
     * @param content 被查找的内容
     * @return 指定内容中是否有表达式匹配的内容
     * @since 3.3.1
     */
    public static boolean contains(String regex, CharSequence content) {
        if (null == regex || null == content) {
            return false;
        }

        final Pattern pattern = PatternPool.get(regex, Pattern.DOTALL);
        return contains(pattern, content);
    }

    /**
     * 指定内容中是否有表达式匹配的内容
     *
     * @param pattern 编译后的正则模式
     * @param content 被查找的内容
     * @return 指定内容中是否有表达式匹配的内容
     * @since 3.3.1
     */
    public static boolean contains(Pattern pattern, CharSequence content) {
        if (null == pattern || null == content) {
            return false;
        }
        return pattern.matcher(content).find();
    }


    /**
     * 获得匹配正则表达式的内容
     *
     * @param allContent        字符串
     * @param regex             正则表达式
     * @param isCaseInsensitive 是否忽略大小写，true忽略大小写，false大小写敏感
     * @return 匹配正则表达式的字符串，组成的List
     */
    public static List<String> getMatchList(final String allContent,
                                            final String regex,
                                            final boolean isCaseInsensitive) {
        List<String> result = new ArrayList<>();
        Pattern pattern = null;
        // 是否大小写敏感
        if (isCaseInsensitive) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(regex);
        }
        // 指定要匹配的字符串
        Matcher matcher = pattern.matcher(allContent);
        // 此处find（）每次被调用后，会偏移到下一个匹配
        while (matcher.find()) {
            // 获取当前匹配的值
            result.add(matcher.group());
        }
        return result;
    }


    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     * 避免正则当作语法处理.
     *
     * @param originalText 原始文本
     * @return 处理后的正则
     */
    public static String convertTextForNotRegex(String originalText) {
        if (StringUtils.isNotBlank(originalText)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (originalText.contains(key)) {
                    originalText = originalText.replace(key, "\\" + key);
                }
            }
        }
        return originalText;
    }

}
