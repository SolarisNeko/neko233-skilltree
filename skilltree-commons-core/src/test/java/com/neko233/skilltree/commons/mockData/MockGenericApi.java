package com.neko233.skilltree.commons.mockData;

import com.neko233.skilltree.commons.core.base.ObjectTypeUtils233;

import java.util.List;

// 模拟泛型数据
public interface MockGenericApi<NUM, T> {

    default List<Class<?>> getErrorUseGenericClassList() {
        return ObjectTypeUtils233.getGenericClassList(this);
    }

    String getData(T data);

    String getNumber(NUM num);

}
