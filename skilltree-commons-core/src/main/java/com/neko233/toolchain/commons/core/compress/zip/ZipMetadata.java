package com.neko233.toolchain.commons.core.compress.zip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2023-01-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZipMetadata {

    private String name;
    private String type;
    private long crc;
    private long compressedSize;
    private long normalSize;
    private long createTimeMs;
    private long lastModifiedTimeMs;
}
