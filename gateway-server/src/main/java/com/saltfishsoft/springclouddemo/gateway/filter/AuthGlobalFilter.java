package com.saltfishsoft.springclouddemo.gateway.filter;

import com.nimbusds.jose.JWSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.function.Consumer;

/**
 * Created by Shihongbing on 2020/7/15.
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered{

    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            //encode防止中文乱码
            String userStr = URLEncoder.encode(jwsObject.getPayload().toString(),"utf-8");
            Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                httpHeader.set("user",userStr);
            };
            ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders).build();
            exchange = exchange.mutate().request(request).build();
        } catch (Exception e) {
            LOGGER.error("parse token error:{}",e.getMessage(),e);
        }
        return chain.filter(exchange);
    }
}
