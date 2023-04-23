package com.neko233.toolchain.idGenerator;

import com.neko233.toolchain.commons.core.annotation.Nullable;

import java.util.List;

public interface IdGenerator {

    String getName();

    /**
     * @return null = 无法获取 / long = id
     */
    @Nullable
    Long nextId() throws IdGeneratorException;

    List<Long> nextIds(int count) throws IdGeneratorException;

    /**
     * 缓存 n 个 ID
     *
     * @param count 缓存 ID 数量
     * @return 是否成功, 有些是不支持缓存的
     * @throws IdGeneratorException ID 生成异常 | id 数量用完了
     */
    boolean cacheId(int count) throws IdGeneratorException;

}