package com.saltfishsoft.springclouddemo.auth.security.integration.authenticator;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by Shihongbing on 2019/10/17.
 */
@Setter
@Getter
public class IntegrationAuthentication {

    /**
     * 认证类型
     */
    protected final String authType;

    /**
     * 认证参数
     */
    protected Map<String,String[]> authParameters;

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }

    public IntegrationAuthentication(String authType, Map<String,String[]> authParameters) {
        this.authType = authType;
        this.authParameters = authParameters;
    }
}
