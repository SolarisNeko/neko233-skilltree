package com.neko233.skilltree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 大对象
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface BigObject {

    String tips() default "";

}
