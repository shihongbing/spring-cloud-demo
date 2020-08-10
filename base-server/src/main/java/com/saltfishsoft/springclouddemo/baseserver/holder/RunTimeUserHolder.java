package com.saltfishsoft.springclouddemo.baseserver.holder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

/**
 * Created by Shihongbing on 2020/7/20.
 * 获取登录用户信息
 */
@Component
@Slf4j
public class RunTimeUserHolder {

    public RunTimeUser getCurrentUser(){
        //从Header中获取用户信息
        RunTimeUser runTimeUser = new RunTimeUser();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        try {
            userStr = URLDecoder.decode(userStr,"utf-8");
        } catch (UnsupportedEncodingException e) {
           log.error("getCurrentUser decode header user info error :{}",e.getMessage(),e);
        }
        if(StringUtils.isNotBlank(userStr)){
            JSONObject userJsonObject = JSONObject.parseObject(userStr);
            runTimeUser.setId(userJsonObject.getString("id"));
            runTimeUser.setAccount(userJsonObject.getString("account"));
            runTimeUser.setUsername(userJsonObject.getString("user_name"));
            JSONArray jsonArray = userJsonObject.getJSONArray("authorities");
            String [] roles = jsonArray.toArray(new String[]{});
            runTimeUser.setRoles(Arrays.asList(roles));
        }else{
            runTimeUser.setAccount("anonymous");
            runTimeUser.setUsername("anonymous");
        }
        return runTimeUser;
    }

}
