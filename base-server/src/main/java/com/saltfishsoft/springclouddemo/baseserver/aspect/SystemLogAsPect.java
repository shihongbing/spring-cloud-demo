package com.saltfishsoft.springclouddemo.baseserver.aspect;

import com.alibaba.fastjson.JSONObject;
import com.saltfishsoft.springclouddemo.baseserver.holder.RunTimeUserHolder;
import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;
import com.saltfishsoft.springclouddemo.baseserver.mq.systemlog.SystemLogSender;
import com.saltfishsoft.springclouddemo.baseserver.util.IpAdrressUtil;
import com.saltfishsoft.springclouddemo.common.annotation.Log;
import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.domain.SystemLog;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Created by Shihongbing on 2020/7/8.
 */
@Aspect
@Component
public class SystemLogAsPect {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(SystemLogAsPect.class);

    @Autowired
    private RunTimeUserHolder runTimeUserHolder;
    @Autowired
    private SystemLogSender systemLogSender;
    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.saltfishsoft.springclouddemo.common.annotation.Log)")
    public void pointcut() {}


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();
        log.info("我在目标方法之前执行！");
        RequestResult result = (RequestResult)point.proceed();
        long endTime = System.currentTimeMillis();
        sendSystemLog(point,result,endTime-beginTime);
        return result;
    }

    private void sendSystemLog(ProceedingJoinPoint point ,RequestResult result,long time){
        LocalDateTime now = LocalDateTime.now();
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        SystemLog systemLog = new SystemLog();
        systemLog.setOperTime(now);
        Log action = method.getAnnotation(Log.class);
        if (action != null) {
            // 注解上的描述
            systemLog.setDescription(action.value());
        }
        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        RunTimeUser runTimeUser = runTimeUserHolder.getCurrentUser();
        String account = runTimeUser.getAccount();
        String userName = runTimeUser.getUsername();
        systemLog.setAccount(account);
        systemLog.setCreateUser(account);
        systemLog.setModifyUser(account);
        systemLog.setCreateTime(now);
        systemLog.setModifyTime(now);
        //请求的参数
        Object[] args = point.getArgs();
        //将参数所在的数组转换成json
        String params = JSONObject.toJSONString(args[0]);
        systemLog.setParams(params);
        systemLog.setContent(result.getData().toString());
        if(result.isSuccess()){
            systemLog.setResult(BusinessConstants.TRUE_STR);
            systemLog.setResultDesc(result.getMessage());
        }else{
            systemLog.setResult(BusinessConstants.FALSE_STR);
            systemLog.setResultDesc(result.getMessage());
        }
        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        systemLog.setClientIp(IpAdrressUtil.getIpAdrress(request));
        log.info("当前登陆人：{},类名:{},方法名:{},参数：{},执行时间：{}",account, className, methodName,params, time);
        systemLogSender.send(systemLog);
    }


}
