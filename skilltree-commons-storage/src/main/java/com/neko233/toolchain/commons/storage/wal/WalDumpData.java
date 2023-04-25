package com.neko233.toolchain.commons.storage.wal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalDumpData {

    private Long sequenceId;
    private Long timestampMs;
    private String dataJson;

}
