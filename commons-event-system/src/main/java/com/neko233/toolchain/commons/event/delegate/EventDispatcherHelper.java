package com.neko233.toolchain.commons.event.delegate;

import com.neko233.toolchain.commons.core.scanner.PackageScanner;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.Set;

@Slf4j
public class EventDispatcherHelper {

    /**
     * 包扫描, 注册 EventHandler | 不支持 Lambda
     *
     * @param dispatcher  目标分发器
     * @param packageName 扫描的包名
     */
    public static void registerByMethodInPackageScan(EventDispatcher dispatcher,
                                                     String packageName) {
        Set<Class<?>> classes = PackageScanner.listClazz(packageName, true, EventListener.class::isAssignableFrom);
        for (Class<?> eventListenerImpl : classes) {
            if (eventListenerImpl.isInterface()) {
                continue;
            }

            EventListener instance;
            try {
                instance = (EventListener) eventListenerImpl.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("className = {} not support no-constructor init", eventListenerImpl.getName(), e);
                continue;
            }
            //
            Class<?> genericRealType = getGenericRealType(eventListenerImpl);
            if (genericRealType == null) {
                continue;
            }

            dispatcher.register(genericRealType, instance);
        }
    }

    /**
     * 泛型实际类型
     *
     * @param clazz 类
     * @return 泛型 <T> 中的实际类型
     */
    private static Class<?> getGenericRealType(Class<?> clazz) {
        try {
            return (Class<?>) ((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        } catch (Exception e) {
            log.error("get <T> real type for className = {}", clazz.getName(), e);
            return null;
        }
    }
}
