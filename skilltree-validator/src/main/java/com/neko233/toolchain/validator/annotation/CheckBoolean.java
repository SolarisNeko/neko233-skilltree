package com.neko233.toolchain.validator.annotation;

import java.lang.annotation.*;

/**
 * 检查布尔值
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckBoolean {

    String tips() default "";

    boolean isMustTrue() default false;

    boolean isMustFalse() default false;


}

