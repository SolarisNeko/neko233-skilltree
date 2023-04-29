package com.neko233.skilltree.aop.proxy;

import com.neko233.skilltree.aop.api.AopApi;

/**
 * @author SolarisNeko
 * Date on 2023-04-29
 */
public interface Aop {

    /**
     * 基于 interface 生成代理
     *
     * @param target 目标
     * @param aopApi 对象
     * @param <T>    接口
     * @return 代理后的对象, return 接口
     */
    <T> T proxyByApi(T target, AopApi aopApi);

    /**
     * 动态代理 by @annotation
     *
     * @param target 目标
     * @param <T>    接口
     * @return 动态代理
     */
    <T> T proxyByAnnotation(T target);

}
