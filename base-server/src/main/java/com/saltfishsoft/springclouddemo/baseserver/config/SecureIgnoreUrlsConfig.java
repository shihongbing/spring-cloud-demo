package com.saltfishsoft.springclouddemo.baseserver.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Shihongbing on 2020/7/15.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class SecureIgnoreUrlsConfig {
    private List<String> urls;
}