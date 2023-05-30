package com.neko233.skilltree.commons.core.base;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 合并 n 个 list
     *
     * @param list 多个数组
     * @param <T>  泛型
     * @return 新的 list
     */
    @SafeVarargs
    public static <T> List<T> union(final List<T>... list) {
        final List<T> result = new ArrayList<>();
        if (ArrayUtils.isEmpty(list)) {
            return result;
        }
        for (List<T> ts : list) {
            result.addAll(ts);
        }
        return result;
    }


    @SafeVarargs
    public static <T> List<T> unionNotNull(final List<T>... list) {
        final List<T> result = new ArrayList<>();
        if (ArrayUtils.isEmpty(list)) {
            return result;
        }
        for (List<T> ts : list) {
            result.addAll(ts);
        }
        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
