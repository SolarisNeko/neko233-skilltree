package com.neko233.skilltree.commons.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class ShaEncryptUtils233Test {

    @Test
    public void sha1() {
        String input = "Hello, World!";
        String sha1Hash = ShaEncryptUtils233.sha1(input);


        Assert.assertEquals("0a0a9f2a6772942557ab5355d76af442f8f65e01", sha1Hash);

    }

    @Test
    public void sha256() {
        String input = "Hello, World!";
        String sha256Hash = ShaEncryptUtils233.sha256(input);
        Assert.assertEquals("dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f", sha256Hash);

    }
}