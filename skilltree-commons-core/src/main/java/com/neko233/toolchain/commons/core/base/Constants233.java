package com.neko233.toolchain.commons.core.base;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

public interface Constants233 {

    /**
     * String to report for unknown information
     */
    String UNKNOWN = "unknown";


    /**
     * 系统文件系统 info
     * The official/approved path for sysfs information. Note: /sys/class/dmi/id
     * symlinks here
     */
    String SYSFS_SERIAL_PATH = "/sys/devices/virtual/dmi/id/";

    /**
     * Unix 时间戳, UTC style
     * The Unix Epoch, a default value when WMI DateTime queries return no value.
     */
    OffsetDateTime UNIX_EPOCH = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);

    /**
     * 数字
     */
    Pattern DIGITS = Pattern.compile("\\d+");


}
