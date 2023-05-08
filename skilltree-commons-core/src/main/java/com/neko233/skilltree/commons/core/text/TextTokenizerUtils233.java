package com.neko233.skilltree.commons.core.text;

import com.neko233.skilltree.commons.core.annotation.NotNull;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.core.base.MapUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko on 2023-05-01
 **/
public class TextTokenizerUtils233 {


    public static Map<String, String> matchFullyToTokenMap(String input,
                                                           String format,
                                                           String... tokens) {
        return matchFullyToTokenMap(input, format, Arrays.asList(tokens));
    }

    /**
     * 完全匹配成 token Map
     *
     * @param input  输入文本
     * @param format 和 input 完全同等长度的 format
     * @param tokens 分词
     * @return 基于分词得到的 Map, 会被覆盖
     */
    @NotNull
    public static Map<String, String> matchFullyToTokenMap(String input,
                                                           String format,
                                                           List<String> tokens) {

        // 不匹配空
        if (StringUtils233.isBlank(input)
                || StringUtils233.isBlank(format)) {
            return MapUtils233.of();
        }


        Map<Character, String> firstCharTokenMap = Optional.ofNullable(tokens).orElse(ListUtils233.of())
                .stream()
                .filter(StringUtils233::isNotBlank)
                .collect(Collectors.toMap(str -> str.charAt(0), Function.identity()));


        Map<String, String> tokenMap = new HashMap<>();

        char[] inputCharArray = input.toCharArray();
        char[] charArray = format.toCharArray();

        int totalLength = charArray.length;

        for (int i = 0; i < totalLength; i++) {
            char c = charArray[i];
            String targetStr = firstCharTokenMap.get(c);
            if (targetStr == null) {
                continue;
            }
            int strLength = targetStr.length();


            char[] formatChar = new char[strLength];
            for (int j = 0; j < strLength; j++) {
                if (i + j >= totalLength) {
                    break;
                }
                formatChar[j] = charArray[i + j];
            }

            // 临时匹配 token
            String tempTokenFormat = new String(formatChar);
            if (!tempTokenFormat.equals(targetStr)) {
                continue;
            }
            char[] inputChars = new char[strLength];
            for (int j = 0; j < strLength; j++) {
                if (i + j > totalLength) {
                    break;
                }
                inputChars[j] = inputCharArray[i + j];
            }
            String tempToken = new String(inputChars);
            tokenMap.put(targetStr, tempToken);
        }
        return tokenMap;
    }
}
