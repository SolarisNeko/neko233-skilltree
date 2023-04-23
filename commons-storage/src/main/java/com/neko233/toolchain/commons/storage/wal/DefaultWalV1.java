package com.neko233.toolchain.commons.storage.wal;

import java.io.File;

/**
 * @author SolarisNeko
 * Date on 2023-01-29
 */
public abstract class DefaultWalV1<D> extends AbstractWalV1<D> {

    /**
     * @param templateFilePath 模板文件, 可不存在的
     * @param dataSchema   数据结构
     * @throws Exception 参数检查异常
     */
    public DefaultWalV1(File templateFilePath, Class<D> dataSchema) throws Exception {
        super(templateFilePath, dataSchema, null);
    }

}
