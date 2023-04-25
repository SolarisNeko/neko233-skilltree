package com.neko233.toolchain.counter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CounterData {

    private String id;
    private String type;
    private Number count;
    private Long nextRefreshMs;

}
