package com.neko233.skilltree.commons.core.regexPattern;

/**
 * 匹配接口
 *
 * @param <T> 匹配的对象类型
 */
@FunctionalInterface
public interface MatcherApi<T> {
    /**
     * 给定对象是否匹配
     *
     * @param t 对象
     * @return 是否匹配
     */
    boolean match(T t);
}
