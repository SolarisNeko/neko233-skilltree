package com.neko233.toolchain.validator.impl;


import com.neko233.toolchain.validator.ValidateApi;
import com.neko233.toolchain.validator.annotation.CheckBoolean;

/**
 * boolean Validator
 */
public class CheckBooleanValidator implements ValidateApi<CheckBoolean, Boolean> {

    @Override
    public Class<? extends CheckBoolean> getAnnotationType() {
        return CheckBoolean.class;
    }

    @Override
    public boolean validateOk(CheckBoolean annotation, Boolean fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        if (annotation.isMustTrue()) {
            if (fieldValue == false) {
                return false;
            }
        }
        if (annotation.isMustFalse()) {
            if (fieldValue == true) {
                return false;
            }
        }

        return true;

    }

    @Override
    public String getReason(CheckBoolean annotation, String fieldName, Boolean fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
