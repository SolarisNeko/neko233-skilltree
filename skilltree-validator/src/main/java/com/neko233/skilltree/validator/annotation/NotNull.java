package com.neko233.skilltree.validator.annotation;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String tips() default "";

}
