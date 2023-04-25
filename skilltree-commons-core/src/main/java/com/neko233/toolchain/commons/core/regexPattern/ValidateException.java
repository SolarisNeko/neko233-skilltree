package com.neko233.toolchain.commons.core.regexPattern;


import com.neko233.toolchain.commons.core.base.StringUtils233;
import com.neko233.toolchain.commons.core.exception.StatefulException;

/**
 * 验证异常
 *
 * @author xiaoleilu
 */
public class ValidateException extends StatefulException {

    private static final long serialVersionUID = 2L;

    public ValidateException() {
    }

    public ValidateException(String msg) {
        super(msg);
    }

    public ValidateException(String messageTemplate, Object... params) {
        super(StringUtils233.format(messageTemplate, params));
    }

    public ValidateException(Throwable throwable) {
        super(throwable);
    }

    public ValidateException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ValidateException(int status, String msg) {
        super(status, msg);
    }

    public ValidateException(int status, Throwable throwable) {
        super(status, throwable);
    }

    public ValidateException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public ValidateException(int status, String msg, Throwable throwable) {
        super(status, msg, throwable);
    }

}
