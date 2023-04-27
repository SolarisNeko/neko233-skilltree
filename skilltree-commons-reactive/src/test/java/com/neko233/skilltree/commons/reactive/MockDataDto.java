package com.neko233.skilltree.commons.reactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2022-10-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockDataDto {

    private String name;

    public static MockDataDto createTest1() {
        return MockDataDto.builder().name("neko233-original").build();
    }

    public static MockDataDto createTest2() {
        return MockDataDto.builder().name("neko233-modified").build();
    }
}
