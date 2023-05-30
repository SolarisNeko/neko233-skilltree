package com.neko233.skilltree.commons.core.file;

import com.neko233.skilltree.commons.core.base.ResourcesJdkUtils233;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileContentUtils233Test {

    @Test
    public void readFileContent() throws IOException {

        File resourceFile = ResourcesJdkUtils233.getResourceFile("file/test_modify_file.txt");

        System.out.println(resourceFile.getAbsolutePath());

        // 读取文件内容
        String fileContent = FileContentUtils233.readFileContent(resourceFile);
        System.out.println("File content:");
        System.out.println(fileContent);

        // 写入文件内容
        String newContent = "This is the new content.";
        FileContentUtils233.writeFileContent(resourceFile, newContent);

        System.out.println("File content after writing:");
        System.out.println(FileContentUtils233.readFileContent(resourceFile));

        // 在指定行插入内容
        int lineNum = 2;
        String insertedContent = "This is the inserted line.";
        FileContentUtils233.insertLine(resourceFile, lineNum, insertedContent);
        System.out.println("File content after inserting a line:");
        System.out.println(FileContentUtils233.readFileContent(resourceFile));



    }

    @Test
    public void writeFileContent() {
    }

    @Test
    public void insertLine() {
    }

    @Test
    public void insertContentAtPosition() {
    }

    @Test
    public void deleteLine() {
    }

    @Test
    public void replaceLine() {
    }
}