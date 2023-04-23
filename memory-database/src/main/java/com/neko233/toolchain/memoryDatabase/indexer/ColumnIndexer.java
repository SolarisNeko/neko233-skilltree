package com.neko233.toolchain.memoryDatabase.indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列索引
 *
 * @author SolarisNeko
 */
public class ColumnIndexer {

    private final Map<String, Map<Object, List<Integer>>> index = new HashMap<>();

    // 进行索引
    public void buildIndex(List<Map<String, Object>> rows) {
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String columnName = entry.getKey();
                Object value = entry.getValue();

                Map<Object, List<Integer>> valueMap = index.computeIfAbsent(columnName, k -> new HashMap<>());
                List<Integer> rowList = valueMap.computeIfAbsent(value, k -> new ArrayList<>());
                rowList.add(i);
            }
        }
    }

    // 查询
    public List<Integer> query(String columnName, Object value) {
        Map<Object, List<Integer>> valueMap = index.get(columnName);
        if (valueMap == null) {
            return new ArrayList<>();
        }

        List<Integer> rowList = valueMap.get(value);
        if (rowList == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(rowList);
    }
}
