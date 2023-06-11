package com.neko233.skilltree.commons.algorithm.compare;

import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import com.neko233.skilltree.commons.algorithm.compare.dto.CompareDataResult;
import com.neko233.skilltree.commons.algorithm.compare.impl.MapCompareData;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用对比算法 <br>
 * ps: 需要比较的对象 T 需要实现 Compare
 *
 * @author LuoHaoJun on 2023-05-01
 **/
@Slf4j
public class CompareAlgorithm {


    /**
     * Map 的比较算法
     *
     * @param oldList 老的
     * @param newList 新的
     * @return CompareDataResult
     */
    public static CompareDataResult<MapCompareData> diffByMap(final List<Map<String, Object>> oldList,
                                                              final List<Map<String, Object>> newList,
                                                              String... keys) {
        return diffByMap(oldList, newList, Arrays.asList(keys));
    }

    public static CompareDataResult<MapCompareData> diffByMap(final List<Map<String, Object>> oldList,
                                                              final List<Map<String, Object>> newList,
                                                              List<String> keys) {
        List<MapCompareData> oldConverList = oldList.stream()
                .map(d -> MapCompareData.of(d, keys))
                .collect(Collectors.toList());
        List<MapCompareData> newConverList = newList.stream()
                .map(d -> MapCompareData.of(d, keys))
                .collect(Collectors.toList());
        return diff(oldConverList, newConverList);
    }

    /**
     * List 的比较算法
     * 输出: toAddList, toUpdateList, toDeleteList, noChangedList
     *
     * @param oldList 老的
     * @param newList 新的
     * @return CompareDataResult
     */
    public static <T extends CompareDataApi> CompareDataResult<T> diff(final List<T> oldList,
                                                                       final List<T> newList) {

        final Map<String, T> oldDataMap = Optional.ofNullable(oldList).orElse(ListUtils233.of())
                .stream()
                .collect(Collectors.toMap(CompareDataApi::primaryKey, Function.identity(), (v1, v2) -> {
                    log.error("primary key not unique! 重复 key = {}", v1.primaryKey());
                    return v1;
                }));

        final Map<String, T> newDataMap = Optional.ofNullable(newList).orElse(ListUtils233.of())
                .stream()
                .collect(Collectors.toMap(CompareDataApi::primaryKey, Function.identity(), (v1, v2) -> {
                    log.error("primary key not unique! 重复 key = {}", v1.primaryKey());
                    return v1;
                }));


        List<T> toAddList = newDataMap.entrySet().stream()
                .filter(entry -> !oldDataMap.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<T> toDeleteList = oldDataMap.entrySet().stream()
                .filter(entry -> !newDataMap.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<T> noChangedList = oldDataMap.entrySet().stream()
                .filter(entry -> newDataMap.containsKey(entry.getKey()))
                .filter(entry -> entry.getValue().isEquals(newDataMap.get(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<T> toUpdateList = newDataMap.entrySet().stream()
                .filter(entry -> oldDataMap.containsKey(entry.getKey()))
                .filter(entry -> !entry.getValue().isEquals(oldDataMap.get(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        CompareDataResult<T> result = new CompareDataResult<>();
        result.setToAddList(toAddList);
        result.setToUpdateList(toUpdateList);
        result.setToDeleteList(toDeleteList);
        result.setNoChangedList(noChangedList);
        return result;

    }
}
