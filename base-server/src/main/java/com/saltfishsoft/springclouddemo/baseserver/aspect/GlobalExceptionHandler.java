package com.saltfishsoft.springclouddemo.baseserver.aspect;

import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.model.result.RequestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 国际化校验异常信息
 4.1、在resources目录下新增ValidationMessages.properties文件，英文为ValidationMessages_en.properties，中文为英文为ValidationMessages_zh_CN.properties，与标准国际化命名类型，都是ValidationMessages开头命令。
 4.2、定义信息key和value
 4.3、在自定义校验注解的String message() default "{properties中定义的key}"中，
 或是在使用校验注解时（@Length(min = 2, max = 20, message = "{properties中定义的key}")）加入properties中定义的key。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";

    @Autowired
    private BasicErrorController basicErrorController;

    @ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
    public RequestResult handleVaildException(Exception e){
        log.error("handleVaildException:{}",e.getMessage(),e);
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        }
        Map<String,String> errorMap = new HashMap<>(16);
        bindingResult.getFieldErrors().forEach((fieldError)->
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return RequestResult.build(BusinessConstants.FAILED_CODE ,"非法参数!" ,errorMap);
    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    public RequestResult jsr303ValidException(Exception e,HttpServletResponse response){
        return resultFormat(e);
    }

    @ExceptionHandler(value = Exception.class)
    public RequestResult defaultException(Exception e,HttpServletResponse response){
        return resultFormat(e);
    }


    private <T extends Throwable> RequestResult resultFormat(Integer code, T ex) {
        log.error(String.format(logExceptionFormat, code, ex.getMessage()),ex);
        RequestResult result = new RequestResult(false,code,ex.getMessage());
        return result;
    }

    private <T extends Throwable> RequestResult resultFormat(T ex) {
        log.error("Capture Exception By GlobalExceptionHandler",ex);
        RequestResult result = new RequestResult(false, BusinessConstants.FAILED_CODE,ex.getMessage());
        return result;
    }

    private <T extends Throwable> RequestResult resultFormat(String msg) {
        log.error("Capture Exception By GlobalExceptionHandler",msg);
        RequestResult result = new RequestResult(false, BusinessConstants.FAILED_CODE,msg);
        return result;
    }

}
