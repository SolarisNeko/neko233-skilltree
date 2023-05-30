package com.neko233.skilltree.commons.algorithm.versionText;

import com.neko233.skilltree.commons.algorithm.versionText.dto.VersionTextCompareResult;
import com.neko233.skilltree.commons.core.base.ListUtils233;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VersionTextCompareUtilsTest {

    @Test
    public void parseUpdateInfo() {
        String currentVersion = "1.0.0";
        String remoteVersion = "2.0.1";

        List<String> versionList = ListUtils233.of(
                "1.0.0",
                "1.0.1",
                "2.0.0",
                "2.0.1");

        List<String> versionChangeList = VersionTextCompareUtils.generateVersionChangeList(currentVersion,
                remoteVersion,
                versionList);

        VersionTextCompareResult result = VersionTextCompareUtils.parseUpdateInfo(currentVersion, versionChangeList);

        Assert.assertEquals(3, result.getVersionChangeList().size());
    }
}