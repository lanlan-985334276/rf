package com.example.rongfu.service.ex;

/**
 * 用户名重复异常类
 */
public class UserNameErrorException extends ServiceException {
    public UserNameErrorException() {
        super();
    }

    public UserNameErrorException(String message) {
        super(message);
    }

    public UserNameErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameErrorException(Throwable cause) {
        super(cause);
    }

    protected UserNameErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
