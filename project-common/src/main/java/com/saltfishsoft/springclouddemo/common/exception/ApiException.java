package com.saltfishsoft.springclouddemo.common.exception;

/**
 * Created by Administrator on 2019/10/9.
 */
public class ApiException extends RuntimeException {
    protected Integer errorCode ;
    protected Object data ;

    public ApiException(Integer errorCode,String message,Object data,Throwable e){
        super(message,e);
        this.errorCode = errorCode ;
        this.data = data ;
    }

    public ApiException(Integer errorCode,String message,Object data){
        this(errorCode,message,data,null);
    }

    public ApiException(Integer errorCode,String message){
        this(errorCode,message,null,null);
    }

    public ApiException(String message,Throwable e){
        this(null,message,null,e);
    }

    public ApiException(){

    }

    public ApiException(Throwable e){
        super(e);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
