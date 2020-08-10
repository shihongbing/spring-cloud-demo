package com.saltfishsoft.springclouddemo.baseserver.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Shihongbing on 2020/6/30.
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;


}
