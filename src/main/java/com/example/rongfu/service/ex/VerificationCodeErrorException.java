package com.example.rongfu.service.ex;

/**
 * 用户名重复异常类
 */
public class VerificationCodeErrorException extends ServiceException {
    public VerificationCodeErrorException() {
        super();
    }

    public VerificationCodeErrorException(String message) {
        super(message);
    }

    public VerificationCodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationCodeErrorException(Throwable cause) {
        super(cause);
    }

    protected VerificationCodeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
