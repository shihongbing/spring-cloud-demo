package com.saltfishsoft.springclouddemo.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务降级处理
 * Created by Shihongbing on 2020/7/20.
 */
@RestController
@Slf4j
public class FallbackController {

    @RequestMapping(value = {"/defaultfallback"},produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> defaultfallback(){
        log.debug("降级操作...");
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","connect to server time out");
        map.put("data",null);
        return map;
    }

}
