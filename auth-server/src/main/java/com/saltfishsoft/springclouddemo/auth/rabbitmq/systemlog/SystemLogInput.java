package com.saltfishsoft.springclouddemo.auth.rabbitmq.systemlog;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by Shihongbing on 2020/7/3.
 */
public interface SystemLogInput {
    String INPUT = "systemLogInput";

    @Input(SystemLogInput.INPUT)
    SubscribableChannel input();
}
