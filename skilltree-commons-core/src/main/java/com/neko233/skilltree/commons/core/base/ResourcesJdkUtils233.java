package com.neko233.skilltree.commons.core.base;

import com.neko233.skilltree.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Java 资源目录
 *
 * @author SolarisNeko
 */
public class ResourcesJdkUtils233 {

    /**
     * 从resources目录下的文本文件中读取 File
     *
     * @return 文件
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    @Nullable
    public static File getResourceFile(String resourceFilePath) throws IOException {
        ClassLoader classLoader = ResourcesJdkUtils233.class.getClassLoader();
        URL resource = classLoader.getResource(resourceFilePath);
        if (resource == null) {
            // 连 resources/ 目录都找不到
            @Nullable String resourceAbsolutePath = getResourceAbsolutePath();
            if (resourceAbsolutePath == null) {
                return null;
            }

            return new File(String.join(System.lineSeparator(), resourceAbsolutePath, resourceFilePath));
        }
        String path = resource.getPath();
        return new File(path);
    }

    /**
     * 获取运行时的 resources/ 绝对路径
     *
     * @return JVM resources/ absolute path
     */
    public static String getResourceAbsolutePath() {
        // 获取 resources 目录的绝对路径
        URL resource = ResourcesJdkUtils233.class.getClassLoader().getResource("");
        if (resource == null) {
            return null;
        }
        return resource.getPath();

    }

    /**
     * 从resources目录下的文本文件中读取内容
     *
     * @param filePath 文件路径（相对于resources目录）
     * @return 文件内容
     * @throws IOException 如果文件读取过程中发生I/O错误
     */
    public static String readTextFile(String filePath) throws IOException {
        InputStream inputStream = ResourcesJdkUtils233.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }

        try (Scanner scanner = new Scanner(inputStream, String.valueOf(StandardCharsets.UTF_8))) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
