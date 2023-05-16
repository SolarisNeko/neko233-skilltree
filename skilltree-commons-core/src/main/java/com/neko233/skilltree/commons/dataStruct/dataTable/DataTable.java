package com.neko233.skilltree.commons.dataStruct.dataTable;

import java.util.HashMap;
import java.util.Map;

public class DataTable<R, C, V> {
    private final Map<R, Map<C, V>> tableDataMap;

    public DataTable() {
        this.tableDataMap = new HashMap<>();
    }

    public void put(R rowKey,
                    C columnKey,
                    V value) {
        Map<C, V> row = tableDataMap.computeIfAbsent(rowKey, k -> new HashMap<>());
        row.put(columnKey, value);
    }

    public V get(R rowKey,
                 C columnKey) {
        Map<C, V> row = tableDataMap.get(rowKey);
        if (row != null) {
            return row.get(columnKey);
        }
        return null;
    }

    public void remove(R rowKey,
                       C columnKey) {
        Map<C, V> row = tableDataMap.get(rowKey);
        if (row != null) {
            row.remove(columnKey);
            if (row.isEmpty()) {
                tableDataMap.remove(rowKey);
            }
        }
    }

    public boolean contains(R rowKey,
                            C columnKey) {
        Map<C, V> row = tableDataMap.get(rowKey);
        return row != null && row.containsKey(columnKey);
    }

    public int size() {
        int size = 0;
        for (Map<C, V> row : tableDataMap.values()) {
            size += row.size();
        }
        return size;
    }

    public void clear() {
        tableDataMap.clear();
    }

    public boolean isEmpty() {
        return tableDataMap.isEmpty();
    }
}
