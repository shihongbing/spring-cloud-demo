package com.saltfishsoft.springclouddemo.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2019/10/9.
 */
@Slf4j
public class LogUtil {

    public static void debug(String message,Object... values){
        if (log.isDebugEnabled()) {
            log.debug(message,values);
        }
    }

    public static void debug(String message){
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }
}
