package com.neko233.skilltree.commons.algorithm.versionText;

import com.neko233.skilltree.commons.algorithm.versionText.dto.VersionTextCompareResult;
import com.neko233.skilltree.commons.core.base.ListUtils233;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 仅针对 "1.0.0" 这种版本号
 *
 * @author SolarisNeko on 2023-05-03
 **/
public class VersionTextCompareUtils {

    public static final int BIG_UPDATE_DIFF_COUNT = 1000 * 1000;
    public static final int SMALL_UPDATE_DIFF_COUNT = 1000;


    public static VersionTextCompareResult parseUpdateInfo(String currentVersion, List<String> newChangeVersionList) {
        Optional<String> maxOpt = newChangeVersionList.stream().max(VersionTextCompareUtils::max);
        if (!maxOpt.isPresent()) {
            return VersionTextCompareResult.noChange(currentVersion);
        }
        String maxVersionText = maxOpt.get();
        int diffCount = Math.abs(compareVersion(currentVersion, maxVersionText));

        if (diffCount < BIG_UPDATE_DIFF_COUNT) {
            return VersionTextCompareResult.smallUpdate(currentVersion, maxVersionText, newChangeVersionList);
        }
        return VersionTextCompareResult.bigUpdate(currentVersion, maxVersionText, newChangeVersionList);
    }

    public static int max(String v1, String v2) {
        return compareVersion(v1, v2);
    }

    public static int min(String v1, String v2) {
        return compareVersion(v2, v1);
    }

    public static boolean isNeedToUpdate(String currentVersion, String otherVersion) {
        return compareVersion(currentVersion, otherVersion) < 0;
    }


    public static int compareVersion(String v1, String v2) {
        int v1Num = getVersionNumber(v1);
        int v2Num = getVersionNumber(v2);
        return v1Num - v2Num;
    }

    public static int getVersionNumber(String version) {
        return getVersionNumber(version, 3);
    }

    public static int getVersionNumber(String version, int slotLength) {
        String[] v1Parts = version.split("\\.");
        if (slotLength <= 0) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < slotLength; i++) {
            // 超出长度, 取 0
            int slotNum = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;

            double pow = Math.pow(1000, slotLength - i);
            sum += (pow * slotNum);
        }
        return sum;
    }


    /**
     * 获取 1.0.0 -> 3.1.1 之间的所有小版本
     *
     * @param currentVersion      当前
     * @param remoteServerVersion 远程服务器
     * @param versionList         版本列表
     * @return 范围内的版本
     */
    public static List<String> generateVersionChangeList(String currentVersion, String remoteServerVersion, List<String> versionList) {

        List<String> updatedVersions = new ArrayList<>();

        for (String version : versionList) {
            if (isVersionGreaterThan(version, currentVersion) && isVersionLessThanEquals(version, remoteServerVersion)) {
                updatedVersions.add(version);
            }
        }

        return updatedVersions;
    }


    // target < other
    private static boolean isVersionLessThan(String target, String otherVersion) {
        if (Objects.equals(target, otherVersion)) {
            return false;
        }
        return !isVersionGreaterThan(target, otherVersion);
    }

    // target <= other
    private static boolean isVersionLessThanEquals(String target, String otherVersion) {
        if (Objects.equals(target, otherVersion)) {
            return true;
        }
        return !isVersionGreaterThan(target, otherVersion);
    }

    private static boolean isVersionGreaterThan(String target, String version2) {
        String[] parts1 = target.split("\\.");
        String[] parts2 = version2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < length; i++) {
            int part1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int part2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;

            if (part1 > part2) {
                return true;
            } else if (part1 < part2) {
                return false;
            }
        }

        return false;
    }


    public static List<String> orderByVersionAsc(List<String> toUpdateVersionList) {
        return toUpdateVersionList.stream()
                .sorted(VersionTextCompareUtils::compareVersion)
                .collect(Collectors.toList());
    }
}
