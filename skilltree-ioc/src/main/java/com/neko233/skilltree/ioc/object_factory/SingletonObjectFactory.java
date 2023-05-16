package com.neko233.skilltree.ioc.object_factory;

import java.lang.reflect.Constructor;

/**
 * @author SolarisNeko on 2023-05-01
 **/
public class SingletonObjectFactory implements ObjectFactory {

    private final Class<?> clazz;
    private volatile Object instance;

    public SingletonObjectFactory(Class<?> clazz) {
        this.clazz = clazz;
    }


    @Override
    public Class<?> getTargetClass() {
        return this.clazz;
    }

    @Override
    public Object getOrCreate() throws Exception {
        if (instance != null) {
            return instance;
        }

        synchronized (SingletonObjectFactory.class) {
            if (instance != null) {
                return instance;
            }
            Constructor<?> noArgsConstructor = clazz.getDeclaredConstructor();
            noArgsConstructor.setAccessible(true);

            Object newObj = noArgsConstructor.newInstance();
            this.instance = newObj;
            return newObj;
        }
    }


}
