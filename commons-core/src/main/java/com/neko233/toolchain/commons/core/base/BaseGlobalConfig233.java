package com.neko233.toolchain.commons.core.base;

import com.neko233.toolchain.commons.core.annotation.NotThreadSafe;
import com.neko233.toolchain.commons.core.file.FileUtils233;

import java.util.Properties;


/**
 * 配置在 resources/neko233.properties | 资源目录下
 * <p>
 * The global configuration utility. See {@code src/main/resources/neko233.properties} for default values.
 * <p>
 * This class is not thread safe if methods manipulating the configuration are
 * used. These methods are intended for use by a single thread at startup,
 * before instantiation of any other OSHI classes. OSHI does not guarantee re-
 * reading of any configuration changes.
 */
@NotThreadSafe
public final class BaseGlobalConfig233 {

    public static final String OSHI_UTIL_MEMOIZER_EXPIRATION = "neko233.util.memoizer.expiration";
    public static final String OSHI_UTIL_WMI_TIMEOUT = "neko233.util.wmi.timeout";
    public static final String OSHI_UTIL_PROC_PATH = "neko233.util.proc.path";
    public static final String OSHI_PSEUDO_FILESYSTEM_TYPES = "neko233.pseudo.filesystem.types";
    public static final String OSHI_NETWORK_FILESYSTEM_TYPES = "neko233.network.filesystem.types";
    public static final String OSHI_OS_WINDOWS_EVENTLOG = "neko233.os.windows.eventlog";
    public static final String OSHI_OS_WINDOWS_PROCSTATE_SUSPENDED = "neko233.os.windows.procstate.suspended";
    public static final String OSHI_OS_WINDOWS_COMMANDLINE_BATCH = "neko233.os.windows.commandline.batch";
    public static final String OSHI_OS_WINDOWS_HKEYPERFDATA = "neko233.os.windows.hkeyperfdata";
    public static final String OSHI_OS_WINDOWS_LOADAVERAGE = "neko233.os.windows.loadaverage";
    public static final String OSHI_OS_WINDOWS_CPU_UTILITY = "neko233.os.windows.cpu.utility";
    public static final String OSHI_OS_WINDOWS_PERFDISK_DIABLED = "neko233.os.windows.perfdisk.disabled";
    public static final String OSHI_OS_WINDOWS_PERFOS_DIABLED = "neko233.os.windows.perfos.disabled";
    public static final String OSHI_OS_WINDOWS_PERFPROC_DIABLED = "neko233.os.windows.perfproc.disabled";
    public static final String OSHI_OS_UNIX_WHOCOMMAND = "neko233.os.unix.whoCommand";
    private static final String OSHI_PROPERTIES = "neko233.properties";
    private static final Properties CONFIG = FileUtils233.readPropertiesFromFilename(OSHI_PROPERTIES);

    private BaseGlobalConfig233() {
    }

    /**
     * Get the property associated with the given key.
     *
     * @param key The property key
     * @return The property value if it exists, or null otherwise
     */
    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    /**
     * Get the {@code String} property associated with the given key.
     *
     * @param key The property key
     * @param def The default value
     * @return The property value or the given default if not found
     */
    public static String get(String key, String def) {
        return CONFIG.getProperty(key, def);
    }

    /**
     * Get the {@code int} property associated with the given key.
     *
     * @param key The property key
     * @param def The default value
     * @return The property value or the given default if not found
     */
    public static int get(String key, int def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : DeviceParseUtil233.parseIntOrDefault(value, def);
    }

    /**
     * Get the {@code double} property associated with the given key.
     *
     * @param key The property key
     * @param def The default value
     * @return The property value or the given default if not found
     */
    public static double get(String key, double def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : DeviceParseUtil233.parseDoubleOrDefault(value, def);
    }

    /**
     * Get the {@code boolean} property associated with the given key.
     *
     * @param key The property key
     * @param def The default value
     * @return The property value or the given default if not found
     */
    public static boolean get(String key, boolean def) {
        String value = CONFIG.getProperty(key);
        return value == null ? def : Boolean.parseBoolean(value);
    }

    /**
     * Set the given property, overwriting any existing value. If the given value is
     * {@code null}, the property is removed.
     *
     * @param key The property key
     * @param val The new value
     */
    public static void set(String key, Object val) {
        if (val == null) {
            CONFIG.remove(key);
        } else {
            CONFIG.setProperty(key, val.toString());
        }
    }

    /**
     * Reset the given property to its default value.
     *
     * @param key The property key
     */
    public static void remove(String key) {
        CONFIG.remove(key);
    }

    /**
     * Clear the configuration.
     */
    public static void clear() {
        CONFIG.clear();
    }

    /**
     * Load the given {@link Properties} into the global configuration.
     *
     * @param properties The new properties
     */
    public static void load(Properties properties) {
        CONFIG.putAll(properties);
    }

    /**
     * Indicates that a configuration value is invalid.
     */
    public static class PropertyException extends RuntimeException {

        private static final long serialVersionUID = -7482581936621748005L;

        /**
         * @param property The property name
         */
        public PropertyException(String property) {
            super("Invalid property: \"" + property + "\" = " + BaseGlobalConfig233.get(property, null));
        }

        /**
         * @param property The property name
         * @param message  An exception message
         */
        public PropertyException(String property, String message) {
            super("Invalid property \"" + property + "\": " + message);
        }
    }
}
