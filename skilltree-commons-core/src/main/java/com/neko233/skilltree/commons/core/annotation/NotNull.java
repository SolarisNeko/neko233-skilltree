package com.neko233.skilltree.commons.core.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String tips() default "";

}
