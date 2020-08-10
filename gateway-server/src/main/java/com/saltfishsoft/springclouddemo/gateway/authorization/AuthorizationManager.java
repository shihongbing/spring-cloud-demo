package com.saltfishsoft.springclouddemo.gateway.authorization;

import com.saltfishsoft.springclouddemo.gateway.config.IgnoreUrlsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by Shihongbing on 2020/7/16.
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext>{

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        String requestPath = exchange.getRequest().getURI().getPath();
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return mono.map(auth -> new AuthorizationDecision(checkAuthorities(auth, requestPath)))
                .defaultIfEmpty(new AuthorizationDecision(false));

    }

    private boolean permitAll(String requestPath) {
        return ignoreUrlsConfig.getUrls().stream().anyMatch(r -> antPathMatcher.match(r, requestPath));
    }

//    //权限校验
//    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
//        Jwt principal = (Jwt) auth.getPrincipal();
//        log.info("访问的URL是：{}用户信息:{}",requestPath, principal.getClaims().get("user_name"));
//        principal.getClaims().get("authorities");
//        return true ;
//    }

    private boolean checkAuthorities(Authentication auth, String requestPath) {
        if (auth instanceof OAuth2Authentication) {
            log.info("requestPath={}", requestPath);
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
            String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
            log.info("clientId is {}", clientId);
            //TODO 记录调用记录
        }
        return true;
    }
}
