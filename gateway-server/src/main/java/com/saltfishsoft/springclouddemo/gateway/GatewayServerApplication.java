package com.saltfishsoft.springclouddemo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * spring.cloud.gateway.discovery.locator.enabled为true，
 * 表明Gateway开启服务注册和发现的功能，并且Spring Cloud Gateway自动根据服务发现为每一个服务创建了一个router，
 * 这个router将以服务名开头的请求路径转发到对应的服务。
 * spring.cloud.gateway.discovery.locator.lowerCaseServiceId是将请求路径上的服务名配置为小写
 * （因为服务注册的时候，向注册中心注册时将服务名转成大写的了）。
 * 如果没有其他需求没有必要在单独配置RouteLocator
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}
