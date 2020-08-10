package com.saltfishsoft.springclouddemo.accserver;

import com.saltfishsoft.springclouddemo.baseserver.mq.systemlog.SystemLogOutPut;
import com.saltfishsoft.springclouddemo.baseserver.repository.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.saltfishsoft.springclouddemo.accserver.repository",
		repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EntityScan(basePackages={"com.saltfishsoft.springclouddemo.accserver.domain","com.saltfishsoft.springclouddemo.common.domain"})
@ComponentScan(basePackages ={"com.saltfishsoft.springclouddemo"})
@EnableFeignClients
@EnableBinding(SystemLogOutPut.class)
public class AccountServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServerApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
