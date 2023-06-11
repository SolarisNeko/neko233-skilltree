package com.neko233.skilltree.commons.algorithm.compare.impl;

import com.neko233.skilltree.commons.algorithm.compare.api.CompareDataApi;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.commons.core.file.FileUtils233;
import com.neko233.skilltree.commons.encrypt.Md5EncryptUtils233;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 数据结构 - 文件对比
 *
 * @author LuoHaoJun on 2023-05-30
 **/
@Data
public class FileCompareData implements CompareDataApi {

    // 绝对路径
    private String absolutePath;
    // 父级路径下的'相对路径'
    private String relativePath;
    // 父级路径
    private String parentPath;
    // 文件
    private File file;
    // 文件内容 md5
    private String fileContentMd5;

    public static FileCompareData create(File file) {
        return new FileCompareData(file.getAbsolutePath(), null);
    }

    /**
     * @param file
     * @param parentPath 父级路径 | 因为有可能要对比 /path/v1/xxx.txt 和 /path/v2/xxx.txt 时, 此处不允许为空
     * @return
     */
    public static FileCompareData create(File file,
                                         String parentPath) {
        return new FileCompareData(file.getAbsolutePath(), parentPath);
    }

    public static FileCompareData create(String absolutePath,
                                         String parentPath) {
        return new FileCompareData(absolutePath,
                parentPath);
    }


    private FileCompareData(String absolutePath,
                            String parentPath) {
        // 绝对路径
        this.absolutePath = absolutePath;

        if (StringUtils233.isNotBlank(parentPath)) {
            this.parentPath = parentPath;
            this.relativePath = getFileRelativePathList(absolutePath, parentPath);
        } else {
            this.parentPath = null;
            this.relativePath = null;
        }

        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new RuntimeException(StringUtils233.format("文件不存在. path = {}", absolutePath));
        }
        this.file = file;
        try {
            // 全量
            String allContent = FileUtils233.readAllContent(file);

            this.fileContentMd5 = Md5EncryptUtils233.getMd5Hash(allContent);
        } catch (IOException e) {
            throw new RuntimeException(StringUtils233.format("没有权限读. path = {}", absolutePath), e);
        }
    }

    @Override
    public List<Object> primaryKeyList() {
        // 优先用相对路径 | 假如只是对比同一个文件路径下
        if (relativePath == null) {
            return ListUtils233.of(this.absolutePath);
        }

        return ListUtils233.of(relativePath);
    }


    public boolean isRelativeFile() {
        return this.relativePath != null;
    }

    /**
     * 获取文件的相对路径
     *
     * @param fileAbsolutePath 文件完整路径
     * @param parentPath       父级路径
     * @return 相对路径
     */
    private static String getFileRelativePathList(String fileAbsolutePath,
                                                  String parentPath) {
        fileAbsolutePath = fileAbsolutePath.replaceAll("\\\\", "/");
        parentPath = parentPath.replaceAll("\\\\", "/");

        if (fileAbsolutePath.startsWith(parentPath)) {
            return fileAbsolutePath.substring(parentPath.length() + 1);
        }
        return fileAbsolutePath;
    }


    // TODO 对 Lombok 支持度不好, IDEA 没有 equals 提示了
    @Override
    public boolean isEquals(Object obj) {
        if (!(obj instanceof FileCompareData)) {
            return false;
        }
        FileCompareData o = (FileCompareData) obj;
        return Objects.equals(this.relativePath, o.relativePath)
                && Objects.equals(this.fileContentMd5, o.fileContentMd5);
    }
}
