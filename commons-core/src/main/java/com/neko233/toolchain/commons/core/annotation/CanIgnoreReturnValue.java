package com.neko233.toolchain.commons.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CanIgnoreReturnValue {

    String tips() default "";

}
