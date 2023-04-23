package com.neko233.toolchain.commons.core.pid;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class ProcessIdUtil {

    public static final String DEFAULT_PROCESS_ID = "-";

    /**
     * 获取进程 ID
     *
     * @return Number String / - (can't not get)
     */
    public static String getProcessId() {
        try {
            // LOG4J2-2126 use reflection to improve compatibility with Android Platform which does not support JMX extensions
            final Class<?> managementFactoryClass = Class.forName("java.lang.management.ManagementFactory");
            final Method getRuntimeMXBean = managementFactoryClass.getDeclaredMethod("getRuntimeMXBean");
            final Class<?> runtimeMXBeanClass = Class.forName("java.lang.management.RuntimeMXBean");
            final Method getName = runtimeMXBeanClass.getDeclaredMethod("getName");

            final Object runtimeMXBean = getRuntimeMXBean.invoke(null);
            final String name = (String) getName.invoke(runtimeMXBean);
            //String name = ManagementFactory.getRuntimeMXBean().getName(); //JMX not allowed on Android
            return name.split("@")[0]; // likely works on most platforms
        } catch (final Exception ex) {
            try {
                return new File("/proc/self").getCanonicalFile().getName(); // try a Linux-specific way
            } catch (final IOException ignoredUseDefault) {
                // Ignore exception.
            }
        }
        return DEFAULT_PROCESS_ID;
    }
}
