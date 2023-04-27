package com.neko233.skilltree.commons.storage.async_dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko on 2023-02-05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSyncScheduleParamDto {

    private Integer initDelay;
    private Integer period;
    private TimeUnit timeUnit;


    public static DataSyncScheduleParamDto temp() {
        return DataSyncScheduleParamDto.builder()
                .initDelay(0)
                .period(10)
                .timeUnit(TimeUnit.SECONDS)
                .build();
    }
}
