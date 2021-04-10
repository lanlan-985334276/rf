package com.example.rongfu.service.ex;

/**
 * 用户名重复异常类
 */
public class FailedException extends ServiceException {
    public FailedException() {
        super();
    }

    public FailedException(String message) {
        super(message);
    }

    public FailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedException(Throwable cause) {
        super(cause);
    }

    protected FailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
