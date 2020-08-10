package com.saltfishsoft.springclouddemo.common.constant;

/**
 * Created by Shihongbing on 2020/6/19.
 */
public class BusinessConstants {

    /**
     * true跟false常量
     */
    public static final String TRUE_STR = "1";
    public static final String FALSE_STR = "0";

    public static final Integer SUCCESS_CODE = 1000;
    public static final String SUCCESS_MESSAGE  = "操作成功";
    public static final Integer FAILED_CODE = 1001;
    public static final String FAILED_MESSAGE = "操作失败";
    public static final String REST_BASE_URL = "/api";

    /**用户密码正则**/
    //public static final String USER_PWD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    //密码至少包含 数字和英文，长度6-20
    //public static final String USER_PWD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    //密码包含 数字,英文,字符中的两种以上，长度6-20
    //public static final String reg = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,20}$";

    // 至少包含数字跟字母，可以有字符
    public static final String USER_PWD_REG = "(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{6,20}$";
    /**身份证号正则*/
    public static final String ID_CARD_NO_REG = "(^[1-9]\\\\d{5}(18|19|20)\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}[0-9Xx]$)|(^[1-9]\\\\d{5}\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}$)";

    /**手机号正则*/
    public static final String PHONE_REG = "^[1][3,4,5,7,8][0-9]{9}$";
    public static final String USER_DEFAULT_PWD = "qwer1234!";

}
