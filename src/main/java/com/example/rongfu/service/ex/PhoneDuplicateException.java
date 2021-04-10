package com.example.rongfu.service.ex;

/**
 * 用户名重复异常类
 */
public class PhoneDuplicateException extends ServiceException {
    public PhoneDuplicateException() {
        super();
    }

    public PhoneDuplicateException(String message) {
        super(message);
    }

    public PhoneDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneDuplicateException(Throwable cause) {
        super(cause);
    }

    protected PhoneDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
