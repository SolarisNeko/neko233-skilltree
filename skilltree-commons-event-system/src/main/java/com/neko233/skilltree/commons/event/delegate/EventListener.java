package com.neko233.skilltree.commons.event.delegate;

/**
 * Delegate 委托模式 = 我提供 data -> EventObserverManager -> 遍历 Observer 进行链式调用
 * Observer 只需要实现下面这个 interface API 即可加入到 EventObserverManager 中, 等待 notify
 *
 * @author SolarisNeko
 * Date on 2022-10-30
 */
public interface EventListener<T> {

    /**
     * @param event 任意数据
     */
    void handle(T event);

}
