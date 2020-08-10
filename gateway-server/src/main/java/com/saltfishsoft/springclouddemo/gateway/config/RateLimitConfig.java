package com.saltfishsoft.springclouddemo.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 限流配置
 * Created by Shihongbing on 2020/7/20.
 * burstCapacity，令牌桶总容量。
    replenishRate，令牌桶每秒填充平均速率。
    key-resolver，用于限流的键的解析器的 Bean 对象的名字。
    它使用 SpEL 表达式根据#{@beanName}从 Spring 容器中获取 Bean 对象。
 */
@Configuration
public class RateLimitConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Primary
    @Bean
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }


//    @Bean
//    @Primary
//    KeyResolver apiKeyResolver() {
//        //按URL限流,即以每秒内请求数按URL分组统计，超出限流的url请求都将返回429状态
//        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
//    }


}
