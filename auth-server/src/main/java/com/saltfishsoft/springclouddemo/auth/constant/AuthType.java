package com.saltfishsoft.springclouddemo.auth.constant;

/**
 * Created by Shihongbing on 2020/7/1.
 */
public enum AuthType {

    PASSWORD("10","密码认证"),
    SMS("20","短信码认证");

    private String code;
    private String means;

    AuthType(String code, String means) {
        this.code = code;
        this.means = means;
    }

    public String code() {
        return this.code;
    }

    public String means() {
        return this.means;
    }
}
