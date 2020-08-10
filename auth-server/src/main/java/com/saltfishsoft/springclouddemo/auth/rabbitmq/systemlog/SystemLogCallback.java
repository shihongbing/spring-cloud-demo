package com.saltfishsoft.springclouddemo.auth.rabbitmq.systemlog;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by Shihongbing on 2020/7/3.
 */
public interface SystemLogCallback {

    String CALLBACKINPUT = "systemLogCallback";

    @Input(SystemLogCallback.CALLBACKINPUT)
    SubscribableChannel callback();

}
