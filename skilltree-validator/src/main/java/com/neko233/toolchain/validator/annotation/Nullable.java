package com.neko233.toolchain.validator.annotation;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nullable {

    String tips() default "";

}
