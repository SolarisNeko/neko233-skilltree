package com.neko233.toolchain.commons.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author SolarisNeko
 * Date on 2023-01-30
 */
public class ListGenerator233 {

    public static <D> List<D> generateObjectByCount(int count, Function<Integer, D> function) {
        return generateObjectByCount(count, 0, function);
    }

    public static <D> List<D> generateObjectByCount(int count, int startIndex, Function<Integer, D> function) {
        final List<D> ds = new ArrayList<>();
        for (int i = startIndex; i < count; i++) {
            ds.add(function.apply(i));
        }
        return ds;
    }
}
