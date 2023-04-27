package com.neko233.skilltree.commons.parser.number;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * 运算符 枚举
 * ps: 只允许单个字符..
 */
@Getter
@AllArgsConstructor
public enum OptEnum {
    /**
     * 运算符
     */
    OP_LEFT_BRACKET("(", "左括号", 0, null),
    OP_RIGHT_BRACKET(")", "右括号", 7, null),

    OP_ADD("+", "加", 2, BigDecimal::add),
    OP_SUB("-", "减", 2, BigDecimal::subtract),
    OP_MULTIPLY("*", "乘", 3, BigDecimal::multiply),
    OP_DIVIDE("/", "除", 3, (p1, p2) -> p1.divide(p2, MathTextCalculator233.CAL_SCALE_SIZE, RoundingMode.HALF_DOWN)),

    OP_FLOOR("_", "取整", 3, (p1, p2) -> p1.divide(p2, 0, RoundingMode.DOWN)),
    OP_MODE("%", "取模", 3, (p1, p2) -> p1.divideAndRemainder(p2)[1]),

    OP_POWER("^", "次方", 4, (p1, p2) -> p1.pow(p2.intValue())),
    /**
     * 这个比较特殊, left 是开根号多少次
     */
    OP_SQRT("/-", "开根号", 4, (left, right) -> {
        BigDecimal temp = right;
        for (int i = 0; i < (left.intValue() - 1); i++) {
            // 取巧实现开根号
            temp = BigDecimal.valueOf(Math.sqrt(temp.doubleValue()));
        }
        return temp;
    }),

    ;


    /**
     * 运算符
     */
    private final String opt;

    /**
     * 运算说明
     */
    private final String optName;

    /**
     * 运算优先级.
     */
    private final Integer optPriority;

    /**
     * 运算函数 Bi-Function
     */
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> calFunction;


    /**
     * 取枚举
     *
     * @param opt 运算符
     * @return 找不到抛异常
     */
    public static OptEnum getEnum(String opt) {
        return Stream.of(OptEnum.values())
                .filter(t -> Objects.equals(opt, t.getOpt()))
                .findFirst().orElseThrow(() -> new RuntimeException("opt not support. 运算符不支持. opt = " + opt));
    }

}