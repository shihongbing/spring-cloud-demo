package com.saltfishsoft.springclouddemo.common.exception;

/**
 * Created by Administrator on 2018/7/30.
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
