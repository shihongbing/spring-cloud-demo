package com.saltfishsoft.springclouddemo.auth.security.integration.authenticator;

import com.saltfishsoft.springclouddemo.auth.constant.AuthType;
import com.saltfishsoft.springclouddemo.auth.model.dto.SecurityUser;
import com.saltfishsoft.springclouddemo.auth.model.dto.UserDto;
import com.saltfishsoft.springclouddemo.auth.service.remote.AccoutFeignClient;
import com.saltfishsoft.springclouddemo.common.exception.AuthException;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Shihongbing on 2019/10/17.
 * 普通密码认证
 */
@Component
@Primary
@Slf4j
public class UsernamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator{


    private AccoutFeignClient accoutFeignClient;
    @Autowired
    public void setAccoutFeignClient(AccoutFeignClient accoutFeignClient) {
        this.accoutFeignClient = accoutFeignClient;
    }

    @Override
    public SecurityUser authenticate(IntegrationAuthentication integrationAuthentication) {
        String username = integrationAuthentication.getAuthParameter("username");
        String password = integrationAuthentication.getAuthParameter("password");
        //调用账号微服务
        RequestResult<UserDto> result = accoutFeignClient.findByAccountEquals(username);
        if(result.getData() == null){
            //new MessageFormat("没有找到帐号为{0}的用户").format(new Object[] { username})
            throw new AuthException(result.getMessage());
        }
        UserDto userDto = result.getData();
        return new SecurityUser(userDto);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        log.debug("UsernamePasswordAuthenticator prepare");
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {
        log.debug("UsernamePasswordAuthenticator complete!");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return AuthType.PASSWORD.code().equals(integrationAuthentication.getAuthType());
    }


}
