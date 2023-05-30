package com.neko233.skilltree.commons.compress.zip;

import com.neko233.skilltree.commons.core.annotation.Out;
import com.neko233.skilltree.commons.core.file.FileUtils233;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * Zip 压缩/解压缩
 */
@Slf4j
public class ZipCompressUtils233 {

    private static final int BUFFER_SIZE = 4096;


    public static File zip(File sourceFileOrDir,
                           @Out File destinationFile) throws IOException {
        // 目标文件, 无论是否存在都会删除
        FileUtils233.deleteQuietly(destinationFile);

        return zip(sourceFileOrDir.getPath(), destinationFile.getPath());
    }

    public static void zip(String sourceFileOrDir,
                           @Out File destinationFile) throws IOException {
        zip(sourceFileOrDir, destinationFile.getPath());
    }


    /**
     * 压缩文件或文件夹
     *
     * @param sourceFileOrDir 源文件或文件夹路径
     * @param destinationPath 压缩文件保存路径
     * @throws IOException 如果压缩过程中发生I/O错误
     */
    public static File zip(String sourceFileOrDir,
                           @Out String destinationPath) throws IOException {

        FileOutputStream fos = new FileOutputStream(destinationPath);
        ZipOutputStream zos = new ZipOutputStream(fos);

        File file = new File(sourceFileOrDir);
        compress(file, file.getName(), zos);

        zos.closeEntry();
        zos.close();

        return new File(destinationPath);
    }

    /**
     * 压缩到 zip output stream
     *
     * @param file     文件
     * @param fileName 文件名
     * @param zos      压缩输出流
     * @throws IOException io 异常
     */
    private static void compress(File file,
                                 String fileName,
                                 @Out ZipOutputStream zos) throws IOException {
        if (file.isHidden()) {
            return;
        }

        if (file.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
                zos.closeEntry();
            }

            File[] children = file.listFiles();
            if (children == null) {
                return;
            }
            for (File childFile : children) {
                compress(childFile, fileName + "/" + childFile.getName(), zos);
            }
        } else {
            byte[] buffer = new byte[BUFFER_SIZE];
            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(fileName));

            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            fis.close();
        }
    }

    /**
     * 解压ZIP文件
     *
     * @param zipFile     ZIP文件路径
     * @param destination 解压目标文件夹路径
     * @throws IOException 如果解压过程中发生I/O错误
     */
    public static void unzip(String zipFile,
                             String destination) throws IOException {
        File destDir = new File(destination);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(zipFile)));
        ZipEntry entry = zis.getNextEntry();

        while (entry != null) {
            String filePath = destination + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zis, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zis.closeEntry();
            entry = zis.getNextEntry();
        }

        zis.close();
    }

    private static void extractFile(ZipInputStream zis,
                                    String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)));
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = zis.read(buffer)) > 0) {
            bos.write(buffer, 0, length);
        }
        bos.close();
    }


    /**
     * 不解压读取 zip file 中的内容
     *
     * @param zipFilePath zipFile 全路径
     */
    public static List<ZipMetadata> readMetadataFromZipFile(String zipFilePath) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            List<ZipMetadata> zipMetadataList = new ArrayList<>();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                String name = entry.getName();
                String type = entry.isDirectory() ? "DIR" : "FILE";
                long crc = entry.getCrc();
                long compressedSize = entry.getCompressedSize();
                long normalSize = entry.getSize();
                long createTimeMs = entry.getCreationTime().toMillis();
                long lastModifiedTimeMs = entry.getLastModifiedTime().toMillis();

                log.info("zip metadata. file Name = {}, type = {}, compressSize = {}, normalSize = {}",
                        name,
                        type,
                        compressedSize,
                        normalSize);
                ZipMetadata zipMetadata = ZipMetadata.builder()
                        .name(name)
                        .type(type)
                        .crc(crc)
                        .compressedSize(compressedSize)
                        .normalSize(normalSize)
                        .createTimeMs(createTimeMs)
                        .lastModifiedTimeMs(lastModifiedTimeMs)
                        .build();
                zipMetadataList.add(zipMetadata);
            }

            zipFile.close();

            return zipMetadataList;
        } catch (IOException ex) {
            log.error("get zip file metadata error", ex);
            throw new RuntimeException(ex);
        }
    }
}
