package com.neko233.toolchain.validator.impl;


import com.neko233.toolchain.validator.ValidateApi;
import com.neko233.toolchain.validator.annotation.NotNull;

/**
 * NotNull Validator
 */
public class NotNullValidator implements ValidateApi<NotNull, Object> {

    @Override
    public Class<? extends NotNull> getAnnotationType() {
        return NotNull.class;
    }

    @Override
    public boolean validateOk(NotNull annotation, Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        return true;
    }

    @Override
    public String getReason(NotNull annotation, String fieldName, Object fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
