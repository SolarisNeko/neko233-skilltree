package com.neko233.skilltree.commons.core.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 切割大文件
 *
 * @author SolarisNeko on 2022-11-11
 **/

public class BigFileSplitUtils233 {

    private static final Logger log = LoggerFactory.getLogger(BigFileSplitUtils233.class);

    /**
     * 切割大文件 | 基于行切割
     *
     * @param bigFilePath            大文件全路径
     * @param outputSmallFilePattern 输出小文件路径 + 占位符 ${index}. demo = /path/to/split/file_${index}
     * @param splitFileCount         切割成多少个小文件. 默认 2 个
     * @throws IOException 异常
     */
    public static void splitBigFile(String bigFilePath, String outputSmallFilePattern, int splitFileCount) throws IOException {
        // 参数检查
        if (splitFileCount <= 0) {
            splitFileCount = 2;
        }

        // 读取行数 | 原生读取
        BufferedReader reader = new BufferedReader(new FileReader(bigFilePath));
        String header = reader.readLine();
        int totalLine = 0;
        if (header != null) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                totalLine++;
            }
        }

        reader.close();

        // 计算切割数据量
        int singleOutputFileSize = totalLine / splitFileCount;
        int generateFileIndex = 0;
        log.info("[output-split-file] start first generate file");

        BufferedWriter bufferedWriter = null;
        reader = new BufferedReader(new FileReader(bigFilePath));
        header = reader.readLine();
        int currentIdx = 0;
        if (header != null) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (currentIdx == 0) {
                    String filePath = outputSmallFilePattern.replace("${index}", String.valueOf(generateFileIndex));
                    bufferedWriter = new BufferedWriter(new FileWriter(filePath));
                }

                bufferedWriter.write(line + "\r\n");

                currentIdx++;

                if (currentIdx == singleOutputFileSize) {
                    generateFileIndex++;
                    log.info("next generate file index = {}", generateFileIndex);
                    if (generateFileIndex != splitFileCount) {
                        currentIdx = 0;
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }
                }
            }

            if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        }

        reader.close();

        log.info("split done. total count = {}", singleOutputFileSize);
    }
}
