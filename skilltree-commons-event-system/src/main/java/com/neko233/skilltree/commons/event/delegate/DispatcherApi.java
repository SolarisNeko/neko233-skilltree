package com.neko233.skilltree.commons.event.delegate;

import com.neko233.skilltree.commons.core.annotation.Nullable;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
public interface DispatcherApi {

    default void sync(Object data) {
        sync(null, data);
    }

    /**
     * 同步处理
     *
     * @param eventName 事件ID
     * @param data    数据
     */
    void sync(String eventName, Object data);

    default <T> Future<?> async(T object) {
        if (object == null) {
            return new FutureTask<>(() -> null);
        }
        String fullClassName = object.getClass().getName();
        return async(fullClassName, object);
    }

    /**
     * 异步处理
     *
     * @param eventId 事件ID
     * @param data    数据
     * @return Future
     */
    Future<?> async(String eventId, Object data);

    /**
     * 注册
     *
     * @param clazz    类
     * @param listener 监听器
     * @return this
     */
    default <T> DispatcherApi register(Class<T> clazz, EventListener listener) {
        if (clazz == null) {
            return this;
        }
        return register(clazz.getName(), listener);
    }

    /**
     * 注册
     *
     * @param eventName 事件ID
     * @param listener  监听者
     * @return this
     */
    DispatcherApi register(@Nullable String eventName, EventListener listener);


    default <T> DispatcherApi unregisterAll(Class<T> clazz) {
        if (clazz == null) {
            return this;
        }
        return unregisterAll(clazz.getName());
    }

    /**
     * 取消注册某个事件
     *
     * @param eventName 事件
     * @return this
     */
    DispatcherApi unregisterAll(String eventName);


    /**
     * 取消注册某个 listener
     *
     * @param eventId       事件
     * @param eventListener 监听器
     */
    void unregister(String eventId, EventListener eventListener);


    default <T> void unregister(Class<T> clazz, EventListener eventListener) {
        if (clazz == null) {
            return;
        }
        unregister(clazz.getName(), eventListener);
    }

}
