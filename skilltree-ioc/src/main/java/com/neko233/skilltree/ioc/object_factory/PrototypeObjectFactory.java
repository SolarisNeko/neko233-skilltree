package com.neko233.skilltree.ioc.object_factory;

import java.lang.reflect.Constructor;

/**
 * @author LuoHaoJun on 2023-05-16
 **/
public class PrototypeObjectFactory implements ObjectFactory {

    private final Class<?> clazz;
    private volatile Constructor<?> noArgsConstructor;

    public PrototypeObjectFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.clazz;
    }

    @Override
    public Object getOrCreate() throws Exception {
        if (noArgsConstructor == null) {
            synchronized (this) {
                if (noArgsConstructor != null) {
                    return noArgsConstructor.newInstance();
                }

                this.noArgsConstructor = clazz.getDeclaredConstructor();
                this.noArgsConstructor.setAccessible(true);
            }
        }

        // 每次都 new
        return noArgsConstructor.newInstance();
    }


}
