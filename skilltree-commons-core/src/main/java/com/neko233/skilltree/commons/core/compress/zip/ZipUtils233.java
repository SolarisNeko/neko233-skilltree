package com.neko233.skilltree.commons.core.compress.zip;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip 压缩/解压缩
 */
@Slf4j
public class ZipUtils233 {

    /**
     * 压缩单个文件 input -> output
     *
     * @param filePath 文件路径
     * @param outPut   输出路径
     */
    public static void compressSingleFile(String filePath, String outPut) {
        try {
            File file = new File(filePath);
            String zipFileName = file.getName().concat(".zip");
            log.info("zipFileName:" + zipFileName);

            // if you want change the menu of output ,just fix here
            // FileOutputStream fos = new FileOutputStream(zipFileName);
            FileOutputStream fos = new FileOutputStream(outPut + File.separator + zipFileName);

            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(file.getName()));

            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();

        } catch (FileNotFoundException ex) {
            System.err.format("The file %s does not exist", filePath);
        } catch (IOException ex) {
            log.error("I/O error: " + ex);
        }
    }

    /**
     * 压缩多个文件
     *
     * @param filePaths 多个路径，第一个 [0] = zip output file
     */
    public static void compressMultipleFiles(String... filePaths) {
        try {
            File firstFile = new File(filePaths[0]);
            String zipFileName = firstFile.getName().concat(".zip");

            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (String aFile : filePaths) {
                zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

                byte[] bytes = Files.readAllBytes(Paths.get(aFile));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }

            zos.close();

        } catch (FileNotFoundException ex) {
            log.error("A file does not exist: " + ex);
        } catch (IOException ex) {
            log.error("I/O error: " + ex);
        }
    }


    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified
     * by destDirectory (will be created if does not exists)
     *
     * @param zipFilePath   zip 文件
     * @param destDirectory 目标文件夹
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a zip entry (file entry)
     *
     * @param zipIn          zip 输入流
     * @param outputFilePath 输出文件路径
     * @throws IOException
     */
    public static void extractFile(ZipInputStream zipIn, String outputFilePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(outputFilePath)));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
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

                log.info("zip metadata. file Name = {}, type = {}, compressSize = {}, normalSize = {}", name, type, compressedSize, normalSize);
                zipMetadataList.add(ZipMetadata.builder()
                        .name(name)
                        .type(type)
                        .crc(crc)
                        .compressedSize(compressedSize)
                        .normalSize(normalSize)
                        .createTimeMs(createTimeMs)
                        .lastModifiedTimeMs(lastModifiedTimeMs)
                        .build()
                );
            }

            zipFile.close();

            return zipMetadataList;
        } catch (IOException ex) {
            log.error("get zip file metadata error", ex);
            throw new RuntimeException(ex);
        }
    }

}