package com.neko233.skilltree.commons.parser.functionText;


import com.neko233.skilltree.commons.core.base.StringUtils233;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko on 2022-01-08
 **/
@Slf4j
public class FunctionTextParser233 {


    /**
     * 解析函数文本
     *
     * @param content 函数内容. 例子: beRewardOnce(once,maxCount=999)
     */
    public static List<FunctionText233> parseBatch(String content) {
        final String[] split = content.split("&&");
        if (ArrayUtils.isEmpty(split)) {
            return new ArrayList<>(0);
        }

        return Arrays.stream(split)
                .map(FunctionTextParser233::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 解析单个 FunctionText
     *
     * @param content 内容
     * @return 函数文本
     */
    public static FunctionText233 parse(String content) {
        if (StringUtils233.isBlank(content)) {
            return null;
        }

        String trimContent = content.trim();
        final String[] funcName2metadata = trimContent.split("\\(|\\)");
        if (funcName2metadata.length > 2) {
            log.error("function content parse error. it format is error. content = {}", content);
            return null;
        }


        final List<String> metadata = new ArrayList<>(0);
        final Map<String, String> kv = new HashMap<>(0);

        String functionName = funcName2metadata[0];
        if (StringUtils.isBlank(functionName)) {
            return null;
        }

        functionName = functionName.trim();

        if (funcName2metadata.length == 1) {
            return FunctionText233.builder()
                    .functionName(functionName.trim())
                    .metadata(Collections.unmodifiableList(metadata))
                    .kv(Collections.unmodifiableMap(kv))
                    .build();
        }

        String properties = funcName2metadata[1];

        String[] keyValueOrTagArray = properties.split(",");
        for (String kvOrTag : keyValueOrTagArray) {
            String[] split = kvOrTag.split("=");
            if (split.length == 1) {
                metadata.add(split[0].trim());
                continue;
            }
            if (split.length == 2) {
                kv.put(split[0].trim(), split[1]);
                continue;
            }
            log.error("unknown what is this function condition. content = {}", kvOrTag);
        }

        return FunctionText233.builder()
                .functionName(functionName)
                .metadata(Collections.unmodifiableList(metadata))
                .kv(Collections.unmodifiableMap(kv))
                .build();
    }


}
