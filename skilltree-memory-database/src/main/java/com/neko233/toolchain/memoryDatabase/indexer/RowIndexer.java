package com.neko233.toolchain.memoryDatabase.indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行数据库 索引
 *
 * @author SolarisNeko
 */
public class RowIndexer {

    private final Map<String, List<Map<String, Object>>> index;

    public RowIndexer(List<Map<String, Object>> data, String key) {
        index = new HashMap<>();
        for (Map<String, Object> item : data) {
            Object value = item.get(key);
            if (value != null) {
                String indexKey = value.toString();
                if (!index.containsKey(indexKey)) {
                    index.put(indexKey, new ArrayList<>());
                }
                index.get(indexKey).add(item);
            }
        }
    }

    public List<Map<String, Object>> get(String key) {
        return index.get(key);
    }
}
