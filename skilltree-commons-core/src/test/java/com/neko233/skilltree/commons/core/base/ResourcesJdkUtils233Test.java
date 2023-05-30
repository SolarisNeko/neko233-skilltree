package com.neko233.skilltree.commons.core.base;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ResourcesJdkUtils233Test {

    @Test
    public void getResourceFile() throws IOException {
        // test resources/
        File resourceFile = ResourcesJdkUtils233.getResourceFile("logback.xml");
        Assert.assertEquals(true, resourceFile != null);
    }

    @Test
    public void readTextFile() throws IOException {
        // test resources/
        String text = ResourcesJdkUtils233.readTextFile("logback.xml");
        Assert.assertEquals(true, StringUtils233.isNotBlank(text));
    }
}