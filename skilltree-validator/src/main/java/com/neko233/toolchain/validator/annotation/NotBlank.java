package com.neko233.toolchain.validator.annotation;

import java.lang.annotation.*;

/**
 * 文本非空
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

    String tips() default "";

}

