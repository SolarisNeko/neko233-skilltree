package com.neko233.skilltree.commons.core.file;

import java.io.*;

/**
 * 文件内容操作工具
 *
 * @author SolarisNeko
 * Date on 2023-01-29
 */
public class FileContentUtils233 {

    public static String readFileContent(File file) throws IOException {
        return readFileContent(file.getPath());
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 如果文件读取过程中发生I/O错误
     */

    public static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static void writeFileContent(File file,
                                        String content) throws IOException {
        writeFileContent(file.getPath(), content);
    }

    /**
     * 写入文件内容
     *
     * @param filePath 文件路径
     * @param content  要写入的内容
     * @throws IOException 如果文件写入过程中发生I/O错误
     */
    public static void writeFileContent(String filePath,
                                        String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            writer.flush();
        }
    }

    public static void insertLine(File file,
                                  int lineNum,
                                  String content) throws IOException {
        insertLine(file.getPath(), lineNum, content);
    }
    /**
     * 在指定行插入内容
     *
     * @param filePath 文件路径
     * @param lineNum  要插入内容的行号（从1开始）
     * @param content  要插入的内容
     * @throws IOException 如果文件读取或写入过程中发生I/O错误
     */
    public static void insertLine(String filePath,
                                  int lineNum,
                                  String content) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            int currentLineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLineNum == lineNum) {
                    writer.write(content);
                    writer.newLine();
                }
                writer.write(line);
                writer.newLine();
                currentLineNum++;
            }

            writer.flush();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Failed to rename temporary file to original file");
            }
        } else {
            throw new IOException("Failed to delete original file");
        }
    }

    /**
     * 在指定行的指定位置插入内容
     *
     * @param filePath 文件路径
     * @param lineNum  行号（从1开始）
     * @param charPos  字符位置（从0开始）
     * @param content  要插入的内容
     * @throws IOException 如果文件读取或写入过程中发生I/O错误
     */
    public static void insertContentAtPosition(String filePath,
                                               int lineNum,
                                               int charPos,
                                               String content) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            int currentLineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLineNum == lineNum) {
                    StringBuilder newLine = new StringBuilder(line);
                    newLine.insert(charPos, content);
                    writer.write(newLine.toString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
                currentLineNum++;
            }
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Failed to rename temporary file to original file");
            }
        } else {
            throw new IOException("Failed to delete original file");
        }
    }

    /**
     * 删除指定行的内容
     *
     * @param filePath 文件路径
     * @param lineNum  要删除内容的行号（从1开始）
     * @throws IOException 如果文件读取或写入过程中发生I/O错误
     */
    public static void deleteLine(String filePath,
                                  int lineNum) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            int currentLineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLineNum != lineNum) {
                    writer.write(line);
                    writer.newLine();
                }
                currentLineNum++;
            }
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("临时文件重命名成源文件失败. Failed to rename temporary file to original file");
            }
        } else {
            throw new IOException("无法删除源文件. Failed to delete original file");
        }
    }

    /**
     * 替换指定行的内容
     *
     * @param filePath   文件路径
     * @param lineNum    行号（从1开始）
     * @param newContent 替换后的内容
     * @throws IOException 如果文件读取或写入过程中发生I/O错误
     */
    public static void replaceLine(String filePath,
                                   int lineNum,
                                   String newContent) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            int currentLineNum = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLineNum == lineNum) {
                    writer.write(newContent);
                } else {
                    writer.write(line);
                }
                writer.newLine();
                currentLineNum++;
            }
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("临时文件重命名报错. Failed to rename temporary file to original file");
            }
        } else {
            throw new IOException("无法删除源文件. Failed to delete original file");
        }
    }

}
