package com.neko233.skilltree.commons.core.business;

import com.neko233.skilltree.commons.core.dataStruct.bloomfilter.BloomFilterSimple;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 兑换码
 *
 * @author SolarisNeko
 */
@Slf4j
public final class RedeemCodeUtils233 {

    // 保存码和对应数字信息
    private static final Map<Integer, String> REDEEM_CODE_MAP = new ConcurrentHashMap<>();
    private static final int MIN_LENGTH = 6;
    private static final int MAX_TRY_COUNT = 5;
    private static final Set<Character> chars = new HashSet<>(61);
    private static final Set<Character> numbers = new HashSet<>(10);
    // 验证是否与数据库已存在的兑换码重复
    private static BloomFilterSimple<String> redeemCodeSet = new BloomFilterSimple<>(10000);

    static {
        for (int i = '0'; i <= '9'; i++) {
            chars.add((char) i);
            numbers.add((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            chars.add((char) i);
        }
    }

    public static List<String> GenerateCodeByBatch(int count) {
        return GenerateCodeByBatch(count, 8);
    }

    public static List<String> GenerateCodeByBatch(int count, int length) {
        List<String> batchRedeemCode = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String e = GenerateRedeemCode(length);
            batchRedeemCode.add(e);
        }
        return batchRedeemCode;
    }

    public static String GenerateRedeemCode(int length) {
        String redeemCode = GenerateRedeemCode(Math.max(length, MIN_LENGTH), "", Collections.emptySet());
        int tryCount = 0;
        while (redeemCodeSet.isMightContains(redeemCode)) {
            if (tryCount >= MAX_TRY_COUNT) {
                return null;
            }
            redeemCode = GenerateRedeemCode(Math.max(length, MIN_LENGTH), "", Collections.emptySet());
            tryCount++;
        }
        return redeemCode;
    }

    public static String GenerateRedeemCode(int length, String prefix) {
        return GenerateRedeemCode(Math.max(length, MIN_LENGTH), prefix, Collections.emptySet());
    }

    /**
     * @param length       兑换码长度
     * @param prefix       前缀
     * @param excludeChars 排除
     * @return 单个兑换码
     */
    public static String GenerateRedeemCode(int length, String prefix, Collection<Character> excludeChars) {
        final Collection<Character> characters = Optional.ofNullable(excludeChars).orElse(new ArrayList<>(0));
        final List<Character> toUseChars = chars.stream().filter(e -> !characters.contains(e)).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIdx = random.nextInt(toUseChars.size());
            Character randomChar = toUseChars.get(randomIdx);
            builder.append(randomChar);
        }
        return builder.toString();
    }

}
