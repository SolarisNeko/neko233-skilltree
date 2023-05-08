package com.neko233.skilltree.commons.core.annotation;

import java.lang.annotation.*;

/**
 * 可突变的 / 可修改的 / 可更换引用的
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mutable {


}
