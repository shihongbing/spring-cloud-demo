package com.saltfishsoft.springclouddemo.common.exception;

/**
 * Created by Administrator on 2018/7/30.
 */
public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidateException() {
        super("参数校验异常");
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }
}
