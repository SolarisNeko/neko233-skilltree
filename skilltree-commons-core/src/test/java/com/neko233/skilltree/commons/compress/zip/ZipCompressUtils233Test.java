package com.neko233.skilltree.commons.compress.zip;

import com.neko233.skilltree.commons.core.base.ResourcesJdkUtils233;
import com.neko233.skilltree.commons.core.file.FileUtils233;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ZipCompressUtils233Test {

    @Test
    public void zip() throws IOException {
        String sourceDirectory = "zip_test";
        File sourceDir = ResourcesJdkUtils233.getResourceFile(sourceDirectory);



        File destination = FileUtils233.createFileX("~/test-data/demo.zip");
        FileUtils233.deleteQuietly(destination);


        // 压缩文件或文件夹
        File zip = ZipCompressUtils233.zip(sourceDir, destination);


        Assert.assertEquals(true, zip.exists());

        FileUtils233.deleteQuietly(destination);
    }
}