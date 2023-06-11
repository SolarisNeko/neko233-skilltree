package com.neko233.skilltree.commons.algorithm.compare.impl;

import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.core.base.MapUtils233;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko on 2023-05-01
 **/
@Data
public class MapCompareData implements CompareDataApi {

    private Map<String, Object> dataMap;
    private List<String> primaryKeyList;

    public static MapCompareData of(Map<String, Object> map
    ) {
        List<String> primaryKeyList = map.keySet().stream().sorted().collect(Collectors.toList());
        return of(map, primaryKeyList);
    }

    /**
     * 构造对比数据结构
     *
     * @param map        数据
     * @param primaryKey Map 中唯一的 key column[]
     * @return 对比的数据结构
     */
    public static MapCompareData of(Map<String, Object> map,
                                    List<String> primaryKey) {
        MapCompareData data = new MapCompareData();
        data.setDataMap(Optional.ofNullable(map).orElse(MapUtils233.of()));
        final List<String> pkList = new ArrayList<>(Optional.ofNullable(primaryKey).orElse(ListUtils233.of()));
        data.setPrimaryKeyList(pkList);
        return data;
    }

    @Override
    public List<Object> primaryKeyList() {
        return primaryKeyList.stream()
                .map(pk -> dataMap.get(pk))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEquals(Object other) {
        return Objects.equals(this, other);
    }
}
