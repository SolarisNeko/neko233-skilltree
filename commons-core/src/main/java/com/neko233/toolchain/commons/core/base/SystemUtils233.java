package com.neko233.toolchain.commons.core.base;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.*;
import java.util.List;
import java.util.Properties;

/**
 * System 操作工具
 *
 * @author SolarisNeko on 2023-01-01
 */
@Slf4j
public class SystemUtils233 {

    /**
     * @return current PID
     */
    public static long getCurrentPID() {
        return Long.parseLong(getRuntimeMXBean().getName().split("@")[0]);
    }


    /**
     * 返回Java虚拟机运行时系统相关属性
     */
    public static RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean();
    }


    /**
     * 返回Java虚拟机编译系统相关属性<br>
     * 如果没有编译系统，则返回{@code null}
     *
     * @return a {@link CompilationMXBean} ，如果没有编译系统，则返回 null
     */
    public static CompilationMXBean getCompilationMXBean() {
        return ManagementFactory.getCompilationMXBean();
    }

    /**
     * 返回Java虚拟机运行下的操作系统相关信息属性
     *
     * @return {@link OperatingSystemMXBean}
     */
    public static OperatingSystemMXBean getOperatingSystemMXBean() {
        return ManagementFactory.getOperatingSystemMXBean();
    }

    /**
     * 获取Java虚拟机中的{@link MemoryPoolMXBean}列表<br>
     * The Java virtual machine can have one or more memory pools. It may add or remove memory pools during execution.
     *
     * @return a list of <tt>MemoryPoolMXBean</tt> objects.
     */
    public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return ManagementFactory.getMemoryPoolMXBeans();
    }

    /**
     * 获取Java虚拟机中的{@link MemoryManagerMXBean}列表<br>
     * The Java virtual machine can have one or more memory managers. It may add or remove memory managers during execution.
     *
     * @return a list of <tt>MemoryManagerMXBean</tt> objects.
     */
    public static List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
        return ManagementFactory.getMemoryManagerMXBeans();
    }

    /**
     * 获取Java虚拟机中的{@link GarbageCollectorMXBean}列表
     *
     * @return {@link GarbageCollectorMXBean}列表
     */
    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans();
    }


    /**
     * 取得系统属性，如果因为Java安全的限制而失败，则将错误打在Log中，然后返回 defaultValue
     *
     * @param name         属性名
     * @param defaultValue 默认值
     * @return 属性值或defaultValue
     * @see System#getProperty(String)
     * @see System#getenv(String)
     */
    public static String get(String name, String defaultValue) {
        String nameValue = get(name, false);
        if (StringUtils233.isBlank(nameValue)) {
            return defaultValue;
        }
        return nameValue;
    }

    /**
     * 取得系统属性，如果因为Java安全的限制而失败，则将错误打在Log中，然后返回 {@code null}
     *
     * @param name       属性名
     * @param isEatError [quiet] 安静模式，不将出错信息打在{@code System.err}中
     * @return 属性值或{@code null}
     * @see System#getProperty(String)
     * @see System#getenv(String)
     */
    public static String get(String name, boolean isEatError) {
        String value = null;
        try {
            value = System.getProperty(name);
        } catch (SecurityException e) {
            if (!isEatError) {
                log.error("Caught a SecurityException reading the system property '{}'; " +
                        "the SystemUtil property value will default to null.", name);
            }
        }

        if (null == value) {
            try {
                value = System.getenv(name);
            } catch (SecurityException e) {
                if (!isEatError) {
                    log.error("Caught a SecurityException reading the system env '{}'; " +
                            "the SystemUtil env value will default to null.", name);
                }
            }
        }

        return value;
    }

    /**
     * 获得System属性
     *
     * @param key 键
     * @return 属性值
     * @see System#getProperty(String)
     * @see System#getenv(String)
     */
    public static String get(String key) {
        return get(key, null);
    }

    /**
     * 获得boolean类型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    /**
     * 获得int类型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 获得long类型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public static long getLong(String key, long defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return Long.parseLong(value);
    }

    /**
     * @return 属性列表
     */
    public static Properties getSystemProperties() {
        return System.getProperties();
    }

    /**
     * 设置系统属性，value为{@code null}表示移除此属性
     *
     * @param key   属性名
     * @param value 属性值，{@code null}表示移除此属性
     */
    public static void set(String key, String value) {
        if (null == value) {
            System.clearProperty(key);
        } else {
            System.setProperty(key, value);
        }
    }

}
