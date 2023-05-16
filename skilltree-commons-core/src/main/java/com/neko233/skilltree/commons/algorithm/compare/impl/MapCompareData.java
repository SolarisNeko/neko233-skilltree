package com.neko233.skilltree.commons.algorithm.compare.impl;

import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.core.base.MapUtils233;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SolarisNeko on 2023-05-01
 **/
@Data
public class MapCompareData implements CompareDataApi {

    private Map<String, Object> dataMap;
    private List<String> primaryKeyList;

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
}
