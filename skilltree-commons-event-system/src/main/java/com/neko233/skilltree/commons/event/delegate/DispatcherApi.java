package com.neko233.skilltree.commons.event.delegate;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
public interface DispatcherApi {

    default void sync(Object data) {
        sync(data.getClass().getName(), data);
    }

    /**
     * 同步处理
     *
     * @param eventId 事件ID
     * @param data    数据
     */
    void sync(String eventId, Object data);

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

    default <T> DispatcherApi register(Class<T> clazz, EventListener listener) {
        if (clazz == null) {
            return this;
        }
        return register(clazz.getName(), listener);
    }

    /**
     * 注册
     *
     * @param eventId  事件ID
     * @param listener 监听者
     * @return this
     */
    DispatcherApi register(String eventId, EventListener listener);


    default <T> DispatcherApi unregisterAll(Class<T> clazz) {
        if (clazz == null) {
            return this;
        }
        return unregisterAll(clazz.getName());
    }

    /**
     * 取消注册某个事件
     *
     * @param eventId 事件
     * @return this
     */
    DispatcherApi unregisterAll(String eventId);


    /**
     * 取消注册某个 listener
     *
     * @param eventId       事件
     * @param eventListener 监听器
     */
    void unregisterListener(String eventId, EventListener eventListener);


    default <T> void unregisterListener(Class<T> clazz, EventListener eventListener) {
        if (clazz == null) {
            return;
        }
        unregisterListener(clazz.getName(), eventListener);
    }

}
