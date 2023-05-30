package com.neko233.skilltree.commons.algorithm.versionText.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author LuoHaoJun on 2023-05-27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VersionTextCompareResult {

    // 是否需要更新
    private boolean isNeedUpdate = false;
    // 当前版本
    private String currentVersion;
    // 远程版本
    private String remoteVersion;
    // 更新级别
    private String updateLevel = "";
    // 变更版本
    private List<String> versionChangeList;

    public static VersionTextCompareResult noChange(String currentVersion) {
        return create(false, currentVersion, currentVersion, null, Constant.UPDATE_LEVEL_NO_CHANGE);
    }

    public static VersionTextCompareResult smallUpdate(String currentVersion, String maxVersionText, List<String> versionChangeList) {
        return create(true, currentVersion, maxVersionText, versionChangeList, Constant.SMALL_UPDATE);
    }

    public static VersionTextCompareResult bigUpdate(String currentVersion, String maxVersionText, List<String> versionChangeList) {
        return create(true, currentVersion, maxVersionText, versionChangeList, Constant.BIG_UPDATE);
    }

    private static VersionTextCompareResult create(boolean isNeedUpdate, String currentVersion, String maxVersionText, List<String> versionChangeList, String bigUpdate) {
        return VersionTextCompareResult.builder()
                .isNeedUpdate(isNeedUpdate)
                .currentVersion(currentVersion)
                .remoteVersion(maxVersionText)
                .updateLevel(bigUpdate)
                .versionChangeList(versionChangeList == null ? Collections.emptyList() : versionChangeList)
                .build();
    }

    public boolean isBigUpdate() {
        return Constant.BIG_UPDATE.equalsIgnoreCase(this.updateLevel);
    }

    interface Constant {
        String UPDATE_LEVEL_NO_CHANGE = "noChange";
        String SMALL_UPDATE = "smallUpdate";
        String BIG_UPDATE = "bigUpdate";
    }
}
