package com.neko233.toolchain.validator.annotation;

import java.lang.annotation.*;

/**
 * 数字校验
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Number {

    String tips() default "";

    /**
     * 必须 正整数
     */
    boolean isMustPositive() default false;

    /**
     * 必须负数
     */
    boolean isMustNegative() default false;

    /**
     * 最小值, 允许最小值.
     */
    int min();

    /**
     * 最大值, 允许的最大值
     */
    int max();

    int[] excludeNumber() default {};

}

