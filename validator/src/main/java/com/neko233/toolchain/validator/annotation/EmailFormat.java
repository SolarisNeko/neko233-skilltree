package com.neko233.toolchain.validator.annotation;

import java.lang.annotation.*;

/**
 * 邮箱格式
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailFormat {

    String tips() default "";

    /**
     * 邮箱, 前缀长度
     */
    int emailPrefixLength() default 20;

    /**
     * 邮箱后缀
     */
    String[] emailSuffix() default {"@neko233.com"};



}

