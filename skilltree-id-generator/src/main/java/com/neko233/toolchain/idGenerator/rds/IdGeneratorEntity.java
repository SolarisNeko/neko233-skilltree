package com.neko233.toolchain.idGenerator.rds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko on 2022-12-29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdGeneratorEntity {

    private String name;
    private String description;
    private long startId;
    private long endId;
    private long step;
    private long currentId;


    public static IdGeneratorEntity getOrDefault(String businessName, IdGeneratorEntity template) {
        if (template == null) {
            return IdGeneratorEntity.builder()
                    .name(businessName)
                    .currentId(1)
                    .startId(1)
                    .endId(100_0000)
                    .step(1)
                    .build();
        }
        if (template.startId >= template.endId
                || template.currentId < template.startId
                || template.currentId > template.endId
        ) {
            throw new RuntimeException("your id_generator template is error. please check your start_id, end_id. data = " + template);
        }
        return template;
    }
}
