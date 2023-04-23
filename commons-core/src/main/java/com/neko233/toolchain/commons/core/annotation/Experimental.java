package com.neko233.toolchain.commons.core.annotation;

import java.lang.annotation.*;

/**
 * 实验性功能
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Experimental {

    String comment();
}
