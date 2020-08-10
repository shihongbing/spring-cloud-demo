package com.saltfishsoft.springclouddemo.baseserver.mq.systemlog;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by Shihongbing on 2020/7/3.
 */
public interface SystemLogOutPut {

    String OUTPUT = "systemLogOutput";

    @Output(SystemLogOutPut.OUTPUT)
    MessageChannel output();
}
