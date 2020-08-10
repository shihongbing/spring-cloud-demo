package com.saltfishsoft.springclouddemo.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

/**
 * Created by Administrator on 2019/3/21.
 */
@Setter
@Getter
public class AccessLog {
    private String token;
    private String path;
    private String body;
    private MultiValueMap<String,String> queryString;
    private long expendTime;
    private int httpCode;
    private String accessTime;

}
