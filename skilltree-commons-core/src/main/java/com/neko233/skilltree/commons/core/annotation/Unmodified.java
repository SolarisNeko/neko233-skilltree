package com.neko233.skilltree.commons.core.annotation;

import java.lang.annotation.*;

/**
 * 不可修改的
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unmodified {


}
