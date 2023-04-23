package com.neko233.toolchain.validator.annotation;

import java.lang.annotation.*;

/**
 * 正则格式
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexFormat {

    String tips() default "";

    String regexFormat();

}

