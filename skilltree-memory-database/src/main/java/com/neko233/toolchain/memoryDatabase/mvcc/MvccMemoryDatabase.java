package com.neko233.toolchain.memoryDatabase.mvcc;


import com.neko233.toolchain.commons.core.base.ListUtils233;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * MVCC in Memory DataBase
 *
 * @author SolarisNeko
 */
public class MvccMemoryDatabase {


    private final List<Map<String, Object>> data = new ArrayList<>(); // 行式数据库
    private final AtomicLong version = new AtomicLong(0); // 数据库版本号
    private final Map<Long, List<Operation>> versionHistory = new ConcurrentHashMap<>(); // 版本号与操作的映射

    // 查询操作
    public List<Map<String, Object>> select() {
        return new ArrayList<>(data);
    }

    // 插入操作
    public synchronized long insert(Map<String, Object> row) {
        long currentVersion = version.incrementAndGet();
        Map<String, Object> newRow = new ConcurrentHashMap<>(row);
        data.add(newRow);

        Operation insertOpt = Operation.builder()
                .type(OperationType.INSERT)
                .newRow(newRow)
                .build();
        versionHistory.put(currentVersion, ListUtils233.of(insertOpt));
        return currentVersion;
    }

    // 更新操作
    public synchronized long update(int rowIndex,
                                    Map<String, Object> newRow) {
        long currentVersion = version.incrementAndGet();
        Map<String, Object> oldRow = data.get(rowIndex);
        Map<String, Object> updatedRow = new ConcurrentHashMap<>(oldRow);
        updatedRow.putAll(newRow);
        data.set(rowIndex, updatedRow);

        Operation updateOpt = Operation.builder()
                .type(OperationType.UPDATE)
                .rowIndex(rowIndex)
                .oldRow(oldRow)
                .newRow(updatedRow)
                .build();
        versionHistory.put(currentVersion, ListUtils233.of(updateOpt));
        return currentVersion;
    }

    // 删除操作
    public synchronized long delete(int rowIndex) {
        long currentVersion = version.incrementAndGet();
        Map<String, Object> oldRow = data.remove(rowIndex);

        Operation oldOpt = Operation.builder()
                .type(OperationType.DELETE)
                .rowIndex(rowIndex)
                .oldRow(oldRow)
                .build();
        versionHistory.put(currentVersion, ListUtils233.of(oldOpt));
        return currentVersion;
    }

    // 指定版本查询操作
    public List<Map<String, Object>> select(long versionNum) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : data) {
            result.add(new ConcurrentHashMap<>(row));
        }
        if (versionNum == 0) {
            return result;
        }
        for (long i = 1; i <= versionNum; i++) {
            for (Operation operation : versionHistory.get(i)) {
                switch (operation.getType()) {
                    case INSERT:
                        result.add(new ConcurrentHashMap<>(operation.getNewRow()));
                        break;
                    case UPDATE:
                        result.set(operation.getRowIndex(), new ConcurrentHashMap<>(operation.getNewRow()));
                        break;
                    case DELETE:
                        result.remove(operation.getRowIndex());
                        break;
                }
            }
        }
        return result;
    }
}