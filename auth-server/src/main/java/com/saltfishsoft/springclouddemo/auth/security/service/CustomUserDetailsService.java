package com.saltfishsoft.springclouddemo.auth.security.service;

import com.saltfishsoft.springclouddemo.auth.constant.MessageConstant;
import com.saltfishsoft.springclouddemo.auth.model.dto.SecurityUser;
import com.saltfishsoft.springclouddemo.auth.security.integration.IntegrationAuthenticationContext;
import com.saltfishsoft.springclouddemo.auth.security.integration.authenticator.IntegrationAuthentication;
import com.saltfishsoft.springclouddemo.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.saltfishsoft.springclouddemo.common.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Shihongbing on 2019/10/16.
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        //判断是否是集成登录
        if (integrationAuthentication == null) {
            throw new AuthException(MessageConstant.ILLEGAL_PARAMS);
        }
        SecurityUser securityUser = (SecurityUser)this.authenticate(integrationAuthentication);
        if(securityUser == null){
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

    /**
     * 调用认证器认证
     * @param integrationAuthentication
     * @return
     */
    private Object authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }




}
