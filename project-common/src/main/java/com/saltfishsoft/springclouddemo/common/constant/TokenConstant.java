package com.saltfishsoft.springclouddemo.common.constant;

/**
 * Created by Shihongbing on 2019/10/15.
 */
public class TokenConstant {

    /** 过期时间  */
    public static final String PAYLOAD_EXP = "exp";

    /** 签发时间  */
    public static final String PAYLOAD_IAT = "iat";

    /** 主题 */
    public static final String PAYLOAD_SUB = "sub";

    /**接收jwt的一方*/
    public static final String PAYLOAD_AUD = "aud";

    /**jwt签发者*/
    public static final String PAYLOAD_ISS = "iss";

    /** 随机数  */
    public static final String PAYLOAD_RDM = "rdm";

    /** token创建标识 */
    public static final String TOKEN_CREATE = "token_create";

    /** token修改标识 */
    public static final String TOKEN_UPDATE = "token_update";

    /** token用户标识 */
    public static final String TOKEN_USER = "token_user";

    /**
     * 授权码缓存标识
     */
    public static final String AUTHORIZ_CODE = "authoriz_code";

    /** 连接符号 . */
    public static final String JOINT_STOP = ".";

    /** 分隔符号 . */
    public static final String SPLIT_STOP = "\\." ;


    /**token签发者*/
    public static final String JWT_TOKEN_ISS = "com.saltfishsoft";
}
