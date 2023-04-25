package com.neko233.toolchain.commons.core.nlp;

/**
 * 相似度匹配工具
 *
 * @author SolarisNeko
 * @date 2022-01-24
 **/
public class TextSimilarUtil {

    public static boolean isTextSimilar(String a, String b, float similarPercent) {
        return levenshteinDistance(a, b) >= similarPercent;
    }

    /**
     * Levenshtein 距离, 是编辑距离的一种。指2个字串之间，由1个String转化成另1个String, 所需的最少编辑操作次数。
     *
     * @param a 内容a
     * @param b 内容b
     * @return 相似度'比例 [0, 1]
     */
    public static float levenshteinDistance(String a, String b) {
        if (a == null && b == null) {
            return 1f;
        }
        if (a == null || b == null) {
            return 0F;
        }
        return 1 - ((float) calculateEditDistance(a, b) / Math.max(a.length(), b.length()));
    }

    /**
     * 编辑距离
     */
    private static int calculateEditDistance(String a, String b) {

        int aLen = a.length();
        int bLen = b.length();

        if (aLen == 0) return aLen;
        if (bLen == 0) return bLen;

        int[][] v = new int[aLen + 1][bLen + 1];
        for (int i = 0; i <= aLen; ++i) {
            for (int j = 0; j <= bLen; ++j) {
                if (i == 0) {
                    v[i][j] = j;
                } else if (j == 0) {
                    v[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    v[i][j] = v[i - 1][j - 1];
                } else {
                    v[i][j] = 1 + Math.min(v[i - 1][j - 1], Math.min(v[i][j - 1], v[i - 1][j]));
                }
            }
        }
        return v[aLen][bLen];
    }

}
