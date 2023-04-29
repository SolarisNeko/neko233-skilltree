package com.neko233.skilltree.commons.event.delegate;

import com.neko233.skilltree.commons.core.base.CollectionUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.commons.core.exception.ThrowableHandler;
import com.neko233.skilltree.commons.event.pool.EventSimpleThreadPoolFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 事件分发器 | event 入口
 *
 * @author SolarisNeko
 * Date on 2022-10-30
 */
@Slf4j
@Data
@AllArgsConstructor
@Builder
public class EventDispatcher implements DispatcherApi {

    /**
     * k-v for dispatcher choose delegate chain .
     */
    private final Map<String, List<EventListener>> eventNameToHandlerMap = new ConcurrentHashMap<>();
    private ThreadPoolExecutor threadPool;
    private ThrowableHandler throwableHandler;


    public EventDispatcher() {
        this(EventSimpleThreadPoolFactory.createSimple("neko233-event-dispatcher"),
                t -> log.error("[{}] happen error. ", EventDispatcher.class.getSimpleName(), t));
    }

    public static EventDispatcher createDefault() {
        return EventDispatcher.builder()
                .threadPool(EventSimpleThreadPoolFactory.createSimple("neko233-event-dispatcher"))
                .throwableHandler((t -> log.error("[{}] happen error. ", EventDispatcher.class.getSimpleName(), t)))
                .build();
    }


    /**
     * 委托执行
     *
     * @param eventName 唯一标识
     * @param data      数据
     */
    @Override
    public void sync(String eventName,
                     Object data) {
        notifyAllObserver(eventName, data);
    }

    private void notifyAllObserver(String eventName,
                                   Object event) {

        List<EventListener> eventListeners = getObserverList(eventName);
        if (CollectionUtils233.isEmpty(eventListeners)) {
            return;
        }

        for (EventListener eventListener : eventListeners) {
            // independent listener
            try {
                eventListener.handle(event);
            } catch (Throwable t) {
                if (throwableHandler == null) {
                    return;
                }
                throwableHandler.handle(t);
            }
        }
    }


    /**
     * 异步执行
     *
     * @param eventName  唯一标识
     * @param event 任意数据
     * @return Future
     */
    @Override
    public Future<?> async(String eventName,
                           Object event) {
        if (threadPool == null) {
            log.error("[{}] your thread pool is null, please check.", EventDispatcher.class.getSimpleName());
            return new FutureTask<>(() -> null);
        }
        return threadPool.submit(() -> sync(eventName, event));
    }


    /**
     * 注册 observer Manager 到 Dispatcher
     *
     * @param eventName 唯一标识名
     * @param observer  观察者
     * @return this
     */
    @Override
    public synchronized EventDispatcher register(String eventName,
                                                 EventListener observer) {
        String realEventName = getRealEventName(eventName, observer);

        eventNameToHandlerMap.merge(realEventName, Collections.singletonList(observer), (v1, v2) -> {
            v1.addAll(v2);
            return v1;
        });
        return this;
    }

    private static String getRealEventName(String eventName, EventListener observer) {
        if (observer == null) {
            return "";
        }
        return eventName == null ? observer.getEventClassName() : eventName;
    }


    /**
     * 取消注册
     *
     * @param eventName 唯一标识名
     * @return this
     */
    @Override
    public synchronized EventDispatcher unregisterAll(String eventName) {
        if (StringUtils233.isBlank(eventName)) {
            return this;
        }
        eventNameToHandlerMap.remove(eventName);
        return this;
    }


    /**
     * @param eventKey      manager 的唯一标识
     * @param eventListener 事件监听器
     * @return isOk?
     */
    public boolean addEventObserver(String eventKey,
                                    EventListener<?> eventListener) {
        eventNameToHandlerMap.merge(eventKey, Collections.singletonList(eventListener), (v1, v2) -> {
            v1.addAll(v2);
            return v1;
        });
        return true;
    }


    /**
     * @param eventName     事件名
     * @param eventListener 事件监听器
     * @return isOk?
     */
    @Override
    public void unregister(String eventName,
                           EventListener eventListener) {
        eventName = getRealEventName(eventName, eventListener);

        List<EventListener> eventListeners = eventNameToHandlerMap.get(eventName);
        eventListeners.remove(eventListener);
    }


    /**
     * 通用检查
     *
     * @param key 唯一标识
     * @return 管理器
     */
    private List<EventListener> getObserverList(String key) {
        if (StringUtils233.isBlank(key)) {
            return null;
        }
        return eventNameToHandlerMap.get(key);
    }

}
