package com.neko233.skilltree.commons.core.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author SolarisNeko
 * Date on 2023-04-13
 */
public class ListUtils233 {

    @SafeVarargs
    public static <V> List<V> of(V... objs) {
        return CollectionUtils233.ofList(objs);
    }

    /**
     * @param list  列表
     * @param index 支持 +/- number
     * @return data
     */
    public static <T> T get(List<T> list,
                            int index) {
        if (null == list) {
            return null;
        }

        // reverse
        if (index < 0) {
            index += list.size();
        }

        return list.get(index);
    }

    /**
     * @param list       列表
     * @param humanIndex 从 1 开始
     * @return data
     */
    public static <T> T getByHumanIndex(List<T> list,
                                        int humanIndex) {
        if (humanIndex == 0) {
            return get(list, 0);
        }
        return get(list, humanIndex - 1);
    }

    /**
     * 联合
     * @param listList 列表
     * @return 联合后的列表
     * @param <T>
     */
    public static <T> List<T> union(List<T>... listList) {
        if (listList == null) {
            return ListUtils233.of();
        }
        int sum = Arrays.stream(listList)
                .filter(Objects::nonNull)
                .mapToInt(List::size)
                .sum();
        final List<T> arrayList = new ArrayList<>(sum);
        for (List<T> list : listList) {
            if (list == null) {
                continue;
            }
            arrayList.addAll(list);
        }
        return arrayList;
    }
}
