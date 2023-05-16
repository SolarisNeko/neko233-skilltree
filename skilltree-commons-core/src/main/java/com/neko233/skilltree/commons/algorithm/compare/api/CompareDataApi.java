package com.neko233.skilltree.commons.algorithm.compare.api;

import com.neko233.skilltree.commons.core.base.ListUtils233;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author LuoHaoJun on 2023-05-15
 **/
public interface CompareDataApi {

    default String primaryKey() {
        if (primaryKeyList() == null) {
            return "";
        }
        List<Object> value = primaryKeyList();
        return Optional.ofNullable(value).orElse(ListUtils233.of())
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("|"));
    }

    List<Object> primaryKeyList();

}
