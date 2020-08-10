package com.saltfishsoft.springclouddemo.common.constant;

/**
 * Created by Administrator on 2019/9/20.
 */
public enum HttpErrorCode {

    BAD_REQUEST(400, "请求的参数个数或格式不符合要求"),
    INVALID_ARGUMENT(400, "请求的参数不正确"),
    UNAUTHORIZED(401, "无权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的地址找不到"),
    METHOD_NOT_ALLOWED(405, "不允许的请求方法"),
    NOT_ACCEPTABLE(406, "不接受的请求"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的Media Type"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "请求服务超时");

    private int code;
    private String means;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    HttpErrorCode(int code, String means){
        this.code = code;
        this.means = means;
    }

}
