package com.saltfishsoft.springclouddemo.common.constant;

/**
 * Created by Shihongbing on 2018/7/30.
 */
public enum LogicDelete {
    NORMAL("0", "正常"),
    RECYCLE("1", "回收站"),
    LOGICDELETED("2", "已删除");

    private String code;
    private String means;

    LogicDelete(String code, String means) {
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
