package com.neko233.skilltree.commons.core.annotation;

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
