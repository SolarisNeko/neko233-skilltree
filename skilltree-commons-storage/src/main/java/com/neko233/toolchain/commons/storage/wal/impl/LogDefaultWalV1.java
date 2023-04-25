package com.neko233.toolchain.commons.storage.wal.impl;

import com.alibaba.fastjson2.JSON;
import com.neko233.toolchain.commons.storage.wal.DefaultWalV1;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * Just a Demo
 *
 * @author SolarisNeko
 * Date on 2023-01-29
 */
@Slf4j
public class LogDefaultWalV1<D> extends DefaultWalV1<D> {
    /**
     * @param templateFilePath 模板文件, 可不存在的
     * @param dataSchema       数据结构
     * @throws Exception 参数检查异常
     */
    public LogDefaultWalV1(File templateFilePath, Class<D> dataSchema) throws Exception {
        super(templateFilePath, dataSchema);
    }

    @Override
    public void flush(List<D> toConsumeDataList) throws Exception {
        for (D d : toConsumeDataList) {
            log.info(JSON.toJSONString(d));
        }
    }
}
