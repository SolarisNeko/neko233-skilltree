package com.neko233.toolchain.commons.core.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiUtil {

    /**
     * Groovy 是否可用
     *
     * @return
     */
    public static boolean isGroovyAvailable() {

        ClassLoader classLoader = SpiUtil.class.getClassLoader();

        try {
            Class<?> bindingClass = classLoader.loadClass("groovy.lang.Binding");
            return bindingClass != null;
        } catch (ClassNotFoundException var2) {
            return false;
        }
    }


    /**
     * SPI get instance
     * need 'META-INF/services/' have API file Name and Impl every line.
     *
     * @param c   class
     * @param <T> any
     * @return class instance
     */
    public static <T> T loadFromServiceLoader(Class<T> c) {
        ServiceLoader<T> loader = ServiceLoader.load(c, getServiceLoaderClassLoader());
        Iterator<T> it = loader.iterator();
        return it.hasNext() ? it.next() : null;
    }

    /**
     * @return SPI CL
     */
    private static ClassLoader getServiceLoaderClassLoader() {
        return SpiUtil.class.getClassLoader();
    }
}