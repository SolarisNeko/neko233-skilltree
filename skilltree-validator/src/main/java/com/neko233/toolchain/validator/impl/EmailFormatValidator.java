package com.neko233.toolchain.validator.impl;


import com.neko233.toolchain.commons.core.base.StringUtils233;
import com.neko233.toolchain.validator.ValidateApi;
import com.neko233.toolchain.validator.annotation.EmailFormat;

/**
 * ValidateEmailFormat Validator
 */
public class EmailFormatValidator implements ValidateApi<EmailFormat, Object> {

    @Override
    public Class<? extends EmailFormat> getAnnotationType() {
        return EmailFormat.class;
    }

    @Override
    public boolean validateOk(EmailFormat annotation, Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        String emailString = String.valueOf(fieldValue);

        String[] emailSuffixList = annotation.emailSuffix();

        String mySuffix = "";
        for (String suffix : emailSuffixList) {
            if (emailString.contains(suffix)) {
                mySuffix = suffix;
            }
        }
        if (StringUtils233.isBlank(mySuffix)) {
            return false;
        }

        String[] split = emailString.split(mySuffix);
        if (split.length != 1) {
            return false;
        }

        String prefix = split[0];
        if (prefix.length() > annotation.emailPrefixLength()) {
            return false;
        }
        return true;
    }

    @Override
    public String getReason(EmailFormat annotation, String fieldName, Object fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
