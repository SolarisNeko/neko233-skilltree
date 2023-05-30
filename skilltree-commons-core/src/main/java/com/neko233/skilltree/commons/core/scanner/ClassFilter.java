package com.neko233.skilltree.commons.core.scanner;

/**
 * 类名称过滤器
 *
 * @author hjj2019
 */
@FunctionalInterface
public interface ClassFilter {

    /**
     * 是否接受当前类?
     *
     * @param clazz 被筛选的类
     * @return 是否符合条件
     */
    boolean accept(Class<?> clazz);


}