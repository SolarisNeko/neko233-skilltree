package com.neko233.skilltree.commons.core.base;

/**
 * 驼峰命名工具
 */
public class CamelCaseUtils233 {


    /**
     * QUERY_HISTORY_REWARD_USER -> queryHistoryRewardUser
     *
     * @param name 命名
     * @return 小驼峰
     */
    public static String bigCamelCaseToLowerCamelCase(String name) {
        // 遇到 "_" 会将后面的 char 大写
        String[] words = ObjectUtils233.getOrDefault(name, "")
                .toLowerCase()
                .split("_");
        StringBuilder sb = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            sb.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1));
        }
        return sb.toString();
    }
}
