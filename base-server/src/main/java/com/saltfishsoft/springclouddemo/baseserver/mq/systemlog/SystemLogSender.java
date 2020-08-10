package com.saltfishsoft.springclouddemo.baseserver.mq.systemlog;

import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Shihongbing on 2020/7/3.
 */
@Component
@Slf4j
public class SystemLogSender {


    @Autowired
    private SystemLogOutPut systemLogOutPut;

    public void send(SystemLog systemLog){
        log.debug("send systemLog:{}", JSONObject.toJSONString(systemLog,true));
        systemLogOutPut.output().send(MessageBuilder.withPayload(systemLog).build());
        log.debug("send systemLog end");
    }

}
