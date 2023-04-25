package com.neko233.toolchain.commons.core.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bit 处理工具
 *
 * @author SolarisNeko on 2023-03-01
 **/
public class BitUtils233 {

    public static Map<Integer, String> bitPrettyOutput(Integer nPowers) {
        return bitPrettyOutput(Collections.singletonList(nPowers));
    }

    /**
     * bit 输出. 只考虑 integer 情况. 因为 bit 能用特别大
     *
     * @param numberList 数字
     * @return Integer -> pretty bit string
     */
    public static Map<Integer, String> bitPrettyOutput(List<Integer> numberList) {
        Map<Integer, String> map = new HashMap<>(numberList.size());
        for (int n = 0; n < numberList.size(); n++) {
            Integer number = numberList.get(n);

            int bitLength = 32;
            String format = String.format("%" + bitLength + "s", Integer.toBinaryString(number));
            String binaryString = format.replace(' ', '0');

            String prettyFormat = StringUtils233.joinByStep(binaryString, 8, ",");
            map.put(number, prettyFormat);
        }
        return map;
    }


}
