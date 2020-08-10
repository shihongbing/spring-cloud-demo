package com.saltfishsoft.springclouddemo.gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Shihongbing on 2019/12/23.
 */
@Slf4j
@Component
public class LogRequestBodyGatewayFilterFactory extends
        AbstractGatewayFilterFactory<LogRequestBodyGatewayFilterFactory.Config> {

    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";

    public LogRequestBodyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String requestBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
            log.info(requestBody);
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

}
