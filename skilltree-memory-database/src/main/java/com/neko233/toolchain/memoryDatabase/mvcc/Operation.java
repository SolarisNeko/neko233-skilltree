package com.neko233.toolchain.memoryDatabase.mvcc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


// 操作类
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {

    private OperationType type;
    private int rowIndex;
    private Map<String, Object> oldRow;
    private Map<String, Object> newRow;

}
