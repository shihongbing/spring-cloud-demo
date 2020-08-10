package com.saltfishsoft.springclouddemo.auth.security.configuration;

import com.saltfishsoft.springclouddemo.auth.model.dto.SecurityUser;
import com.saltfishsoft.springclouddemo.common.constant.TokenConstant;
import com.saltfishsoft.springclouddemo.common.util.RandomUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shihongbing on 2020/7/21.
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户信息设置到JWT中
        info.put("id", securityUser.getId());
        info.put("account", securityUser.getAccount());
        info.put(TokenConstant.PAYLOAD_IAT, new Date());
        info.put(TokenConstant.PAYLOAD_ISS, TokenConstant.JWT_TOKEN_ISS);
        info.put(TokenConstant.PAYLOAD_RDM, RandomUtil.generateString(8));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
