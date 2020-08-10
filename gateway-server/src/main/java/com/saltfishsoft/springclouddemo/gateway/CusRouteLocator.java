package com.saltfishsoft.springclouddemo.gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Shihongbing on 2019/12/23.
 */
//@Configuration
//@Slf4j
public class CusRouteLocator {

//    @Autowired
//    private LogRequestBodyGatewayFilterFactory logRequestBodyGatewayFilterFactory;
//
//
//
//    @Bean("myLocator")
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-server", r -> r.path("/auth-server/**")
//                        .and()
//                        .readBody(String.class, requestBody -> true)
//                        .filters( f -> f.stripPrefix(1)
//                                .filter(logRequestBodyGatewayFilterFactory.apply(new LogRequestBodyGatewayFilterFactory.Config())))
//                        .uri("lb://auth-server"))
//                .build();
//    }
//


}
