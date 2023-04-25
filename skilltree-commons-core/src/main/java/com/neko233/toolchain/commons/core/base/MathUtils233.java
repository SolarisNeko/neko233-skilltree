package com.neko233.toolchain.commons.core.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils233 {

    /**
     * 百分比
     *
     * @param molecule    分子
     * @param denominator 分母
     * @return 保留两位小数
     */
    public static BigDecimal percent(BigDecimal molecule, BigDecimal denominator) {
        if (molecule == null || denominator == null) {
            return BigDecimal.ZERO;
        }
        return divide(molecule.multiply(BigDecimal.valueOf(100)), denominator.floatValue());
    }


    /**
     * 除法
     */
    public static BigDecimal divide(Number molecule, Number denominator) {
        if (molecule == null || denominator == null) {
            return BigDecimal.ZERO;
        }
        return divide(BigDecimal.valueOf(molecule.floatValue()), BigDecimal.valueOf(denominator.floatValue()));
    }

    /**
     * @param molecule    分子
     * @param denominator 分母
     * @return BigDecimal 返回值
     */
    public static BigDecimal divide(BigDecimal molecule, BigDecimal denominator) {
        return divide(molecule, denominator, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal molecule, BigDecimal denominator, Integer scale, RoundingMode roundingMode) {
        if (molecule == null || denominator == null) {
            return BigDecimal.ZERO;
        }
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return molecule.divide(denominator, scale, roundingMode);
    }

    public static BigDecimal multiply(Number a, Number b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return multiply(new BigDecimal(a.toString()), new BigDecimal(b.toString()));
    }

    /**
     * result = a - b
     *
     * @return BigDecimal 差值
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return a.multiply(b);
    }

    /**
     * result = a - b
     *
     * @return BigDecimal 差值
     */
    public static BigDecimal subtract(Number a, Number b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return subtract(new BigDecimal(a.toString()), new BigDecimal(b.toString()));
    }

    /**
     * result = a - b
     *
     * @return BigDecimal 差值
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return a.subtract(b);
    }

    public static BigDecimal add(Number a, Number b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return add(new BigDecimal(a.toString()), new BigDecimal(b.toString()));
    }


    /**
     * result = a + b
     *
     * @return BigDecimal 返回值
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return a.add(b);
    }

    /**
     * 是否为数字，支持包括：
     *
     * <pre>
     * 1、10进制
     * 2、16进制数字（0x开头）
     * 3、科学计数法形式（1234E3）
     * 4、类型标识形式（123D）
     * 5、正负数标识形式（+123、-234）
     * </pre>
     *
     * @param str 字符串值
     * @return 是否为数字
     */
    public static boolean isNumber(CharSequence str) {
        if (StringUtils233.isBlank(str)) {
            return false;
        }
        char[] chars = str.toString().toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        int start = (chars[0] == '-' || chars[0] == '+') ? 1 : 0;
        if (sz > start + 1) {
            if (chars[start] == '0' && (chars[start + 1] == 'x' || chars[start + 1] == 'X')) {
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
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
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (false == foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
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
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                // not allowing L with an exponent
                return foundDigit && !hasExp;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return false == allowSigns && foundDigit;
    }

    /**
     * power 次方方
     *
     * @param number     数字
     * @param powerLevel 平方次数
     * @return n次方
     */
    public static BigDecimal power(Number number, int powerLevel) {
        return BigDecimal.valueOf(Math.pow(number.doubleValue(), powerLevel));
    }
}
