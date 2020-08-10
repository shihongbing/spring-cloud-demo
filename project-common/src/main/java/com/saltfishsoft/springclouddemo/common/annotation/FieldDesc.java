package com.saltfishsoft.springclouddemo.common.annotation;

import java.lang.annotation.*;

/**
 * Created by Shihongbing on 2020/6/19.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value= ElementType.FIELD)
public @interface FieldDesc {
    String value() ;
}
