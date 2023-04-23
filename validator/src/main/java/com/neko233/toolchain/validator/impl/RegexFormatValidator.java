package com.neko233.toolchain.validator.impl;

import com.neko233.toolchain.common.base.RegexUtils233;

import com.neko233.toolchain.validator.ValidateApi;
import com.neko233.toolchain.validator.annotation.RegexFormat;

/**
 * ValidateNumber Validator
 */
public class RegexFormatValidator implements ValidateApi<RegexFormat, String> {

    @Override
    public Class<? extends RegexFormat> getAnnotationType() {
        return RegexFormat.class;
    }

    @Override
    public boolean validateOk(RegexFormat annotation, String fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        // 无配置自动放行
        if (annotation.regexFormat() == null) {
            return true;
        }
        return RegexUtils233.isMatch(annotation.regexFormat(), fieldValue);
    }

    @Override
    public String getReason(RegexFormat annotation, String fieldName, String fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
