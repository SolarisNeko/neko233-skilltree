package com.neko233.skilltree.validator.impl;


import com.neko233.skilltree.commons.core.base.StringUtils233;
import com.neko233.skilltree.validator.annotation.Number;
import com.neko233.skilltree.validator.ValidateApi;

import java.util.Objects;

/**
 * ValidateNumber Validator
 */
public class NumberValidator implements ValidateApi<Number, java.lang.Number> {

    @Override
    public Class<? extends Number> getAnnotationType() {
        return Number.class;
    }

    @Override
    public boolean validateOk(Number annotation, java.lang.Number fieldValue) {

        if (fieldValue == null) {
            return false;
        }

        int intValue = fieldValue.intValue();
        // 负整数
        if (annotation.isMustNegative()) {
            if (intValue > 0) {
                return false;
            }
        }
        // 正整数
        if (annotation.isMustPositive()) {
            if (intValue < 0) {
                return false;
            }
        }

        int min = annotation.min();
        int max = annotation.max();

        if (min == 0 && max == 0) {
            return true;
        }

        int value = intValue;
        if (value < min) {
            return false;
        }
        if (value > max) {
            return false;
        }
        int[] excludeNumber = annotation.excludeNumber();
        if (excludeNumber != null) {
            for (int i : excludeNumber) {
                if (Objects.equals(i, fieldValue)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String getReason(Number annotation, String fieldName, java.lang.Number fieldValue) {
        return StringUtils233.format("tips = {}. field = {}. value is {}", annotation.tips(), fieldName, fieldValue);
    }

}
