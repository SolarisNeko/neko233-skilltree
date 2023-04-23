package com.neko233.toolchain.memoryDatabase.columnDatabase;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 稀疏列数据库
 *
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class SparseColumnMemoryDatabase {

    // 列向量 <columnName : <RowNumber, Value>>
    private final Map<String, Map<Integer, Object>> columnDatabase = new ConcurrentHashMap<>();

    // 添加数据
    public void put(String columnName, int row, Object value) {
        Map<Integer, Object> column = columnDatabase.computeIfAbsent(columnName, k -> new HashMap<>());
        column.put(row, value);
    }

    // 获取数据
    public Object get(String columnName, int row) {
        Map<Integer, Object> column = columnDatabase.get(columnName);
        return column == null ? null : column.get(row);
    }

    // 获取列向量
    public Map<Integer, Object> getColumn(String columnName) {
        return columnDatabase.get(columnName);
    }

    // 获取行向量
    public Map<String, Object> getRow(int row) {
        Map<String, Object> rowVector = new HashMap<>();
        for (Map.Entry<String, Map<Integer, Object>> column : columnDatabase.entrySet()) {
            Object value = column.getValue().get(row);
            if (value != null) {
                rowVector.put(column.getKey(), value);
            }
        }
        return rowVector;
    }
}

