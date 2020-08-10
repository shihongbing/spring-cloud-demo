package com.saltfishsoft.springclouddemo.auth;

import com.saltfishsoft.springclouddemo.auth.rabbitmq.systemlog.SystemLogCallback;
import com.saltfishsoft.springclouddemo.baseserver.mq.systemlog.SystemLogOutPut;
import com.saltfishsoft.springclouddemo.baseserver.repository.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.saltfishsoft.springclouddemo.auth.repository",
		repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EntityScan(basePackages={"com.saltfishsoft.springclouddemo.auth.domain","com.saltfishsoft.springclouddemo.common.domain"})
@ComponentScan("com.saltfishsoft.springclouddemo")
@EnableFeignClients(basePackages = {"com.saltfishsoft.springclouddemo.auth.service.remote"})
@EnableBinding({SystemLogOutPut.class, SystemLogCallback.class})
public class AuthServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}



}
