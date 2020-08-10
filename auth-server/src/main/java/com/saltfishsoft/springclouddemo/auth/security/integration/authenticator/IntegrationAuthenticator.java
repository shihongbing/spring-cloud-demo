package com.saltfishsoft.springclouddemo.auth.security.integration.authenticator;

/**
 * Created by Shihongbing on 2019/10/17.
 */
public interface IntegrationAuthenticator {

    /**
     * 处理集成认证 不同的认证类型返回不同的认证结果
     * @param integrationAuthentication
     * @return
     */
    Object authenticate(IntegrationAuthentication integrationAuthentication);


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /** 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);
}
