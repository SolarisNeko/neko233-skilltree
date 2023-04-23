package com.neko233.toolchain.validator.impl;


import com.neko233.toolchain.validator.ValidateApi;
import com.neko233.toolchain.validator.annotation.NotBlank;

/**
 * NotBlank Validator
 */
public class NotBlankValidator implements ValidateApi<NotBlank, String> {

    @Override
    public Class<? extends NotBlank> getAnnotationType() {
        return NotBlank.class;
    }

    @Override
    public boolean validateOk(NotBlank annotation, String fieldValue) {
        return StringUtils233.isNotBlank(fieldValue);
    }

    @Override
    public String getReason(NotBlank annotation, String fieldName, String fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
