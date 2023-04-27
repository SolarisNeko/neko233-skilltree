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
import java.util.Map;
import java.util.Set;
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
    private final Map<String, Set<EventListener>> dispatcherMap = new ConcurrentHashMap<>();
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
     * @param eventKey 唯一标识
     * @param data     数据
     */
    @Override
    public void sync(String eventKey,
                     Object data) {
        notifyAllObserver(eventKey, data);
    }

    private void notifyAllObserver(String key,
                                   Object data) {
        Set<EventListener> eventListeners = getObserverList(key);
        if (CollectionUtils233.isEmpty(eventListeners)) {
            return;
        }

        for (EventListener eventListener : eventListeners) {
            // independent listener
            try {
                eventListener.handle(data);
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
     * @param key  唯一标识
     * @param data 任意数据
     * @return Future
     */
    @Override
    public Future<?> async(String key,
                           Object data) {
        if (threadPool == null) {
            log.error("[{}] your thread pool is null, please check.", EventDispatcher.class.getSimpleName());
            return new FutureTask<>(() -> null);
        }
        return threadPool.submit(() -> sync(key, data));
    }


    /**
     * 注册 observer Manager 到 Dispatcher
     *
     * @param key      唯一标识名
     * @param observer 观察者
     * @return this
     */
    @Override
    public synchronized EventDispatcher register(String key,
                                                 EventListener observer) {
        dispatcherMap.merge(key, Collections.singleton(observer), (v1, v2) -> {
            v1.addAll(v2);
            return v1;
        });
        return this;
    }


    /**
     * 取消注册
     *
     * @param eventKey 唯一标识名
     * @return this
     */
    @Override
    public synchronized EventDispatcher unregisterAll(String eventKey) {
        if (StringUtils233.isBlank(eventKey)) {
            return this;
        }
        dispatcherMap.remove(eventKey);
        return this;
    }


    /**
     * @param eventKey      manager 的唯一标识
     * @param eventListener 事件监听器
     * @return isOk?
     */
    public boolean addEventObserver(String eventKey,
                                    EventListener<?> eventListener) {
        dispatcherMap.merge(eventKey, Collections.singleton(eventListener), (v1, v2) -> {
            v1.addAll(v2);
            return v1;
        });
        return true;
    }


    /**
     * @param eventKey      manager 的唯一标识
     * @param eventListener 事件监听器
     * @return isOk?
     */
    @Override
    public void unregisterListener(String eventKey,
                                   EventListener eventListener) {
        Set<EventListener> eventListeners = dispatcherMap.get(eventKey);
        eventListeners.remove(eventListener);
    }


    /**
     * 通用检查
     *
     * @param key 唯一标识
     * @return 管理器
     */
    private Set<EventListener> getObserverList(String key) {
        if (StringUtils233.isBlank(key)) {
            return null;
        }
        return dispatcherMap.get(key);
    }

}
