package com.neko233.skilltree.commons.core.file.visitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipOutputStream;

/**
 * @author SolarisNeko
 * Date on 2023-01-29
 */
public class ZipTreeFileVisitorTest {


    public void t() {
        ZipOutputStream zos;
        String dirPath = "H:/Sessions";
        Path sourceDir = Paths.get(dirPath);

        try {
            String zipFileName = dirPath.concat(".zip");
            zos = new ZipOutputStream(new FileOutputStream(zipFileName));

            Files.walkFileTree(sourceDir, new ZipTreeFileVisitor(sourceDir));

            zos.close();
        } catch (IOException ex) {
            System.err.println("I/O Error: " + ex);
        }
    }

}