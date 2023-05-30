package com.neko233.skilltree.commons.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class Md5EncryptUtils233Test {

    @Test
    public void getMd5Hash() {
        String demo = "hello";

        String md5Hash = Md5EncryptUtils233.getMd5Hash(demo);

        Assert.assertEquals("5d41402abc4b2a76b9719d911017c592", md5Hash);
    }
}