package com.saltfishsoft.springclouddemo.auth.rabbitmq.systemlog;

import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.auth.repository.SystemLogRepository;
import com.saltfishsoft.springclouddemo.auth.service.local.ISystemLogService;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Shihongbing on 2020/7/3.
 */
@Component
@Slf4j
@EnableBinding(SystemLogInput.class)
public class SystemLogReceiver {

    @Autowired
    private SystemLogRepository systemLogRepository;
    /**
     * @SendTo注解，标注方法将返回对象包装为Message对象发送到指定的队列。
    当不指定@SendTo的value值时，将通过默认exchange发送到默认队列
    当指定@SendTo的value值时，将通过默认或指定的exchange发送到指定队列
    对于value值取值为：exchange/routingkey形式
    a/b代表exchange为a, routingkey为b
    仅a代表，routingkey为a
     @SendTo只在收到的Message没有replyTo属性时使用
     原文链接：https://blog.csdn.net/fly_leopard/article/details/100691345
     * @param systemLog
     * @return
     */
    @StreamListener(SystemLogInput.INPUT)
    @SendTo(SystemLogCallback.CALLBACKINPUT)
    public String process(SystemLog systemLog){
        log.debug("SystemLogReceiver receiving message :{} ", JSONObject.toJSONString(systemLog,true));
        systemLogRepository.save(systemLog);
        //日志保存成功后,通知SendTo,toString返回的是日志Id,下面将收到toString的内容
        return systemLog.toString();
    }

    @StreamListener(SystemLogCallback.CALLBACKINPUT)
    public void callback(String text){
        //接收上面SendTo的消息
        log.debug("message has recived : {} ", text);
    }


}
