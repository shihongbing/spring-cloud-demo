package com.saltfishsoft.springclouddemo.baseserver.config;

/**
 * Created by Shihongbing on 2020/7/14.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * session托管到redis
 * maxInactiveIntervalInSeconds: 设置 Session 失效时间，
 * 使用 Redis Session 之后，原 Spring Boot 的 server.servlet.session.timeout 属性不再生效。
 * RedisFlushMode有两个参数：ON_SAVE（表示在response commit前刷新缓存），IMMEDIATE（表示只要有更新，就刷新缓存）
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 7200, flushMode= FlushMode.ON_SAVE, redisNamespace = "WEB_SESSION")
public class RedisSessionConfig {

}