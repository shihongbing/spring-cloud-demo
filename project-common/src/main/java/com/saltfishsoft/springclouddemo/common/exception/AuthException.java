package com.saltfishsoft.springclouddemo.common.exception;

/**
 * Created by Shihongbing on 2019/10/17.
 */
public class AuthException extends RuntimeException {

    public AuthException() {
        super("认证异常");
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

}