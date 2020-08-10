package com.saltfishsoft.springclouddemo.baseserver.util;

//import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.RememberMeAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;

import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;

/**
 * Created by Shihongbing on 2020/6/30.
 */
public class BusinessUtils {

//    public static RunTimeUser getRunTimeUser(){
//        AbstractAuthenticationToken abstractAuthenticationToken = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        if(abstractAuthenticationToken instanceof UsernamePasswordAuthenticationToken){
//            UsernamePasswordAuthenticationToken token =(UsernamePasswordAuthenticationToken) abstractAuthenticationToken;
//            User userDetail = (User) token.getPrincipal();
//            return new RunTimeUser(userDetail.getUsername(),userDetail.getAuthorities());
//        }else if(abstractAuthenticationToken instanceof RememberMeAuthenticationToken){
//            RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) abstractAuthenticationToken;
//            User userDetail = (User) token.getPrincipal();
//            return new RunTimeUser(userDetail.getUsername(),userDetail.getAuthorities());
//        }
//        return null;
//    }

    public static RunTimeUser getRunTimeUser(){
        return null;
    }



}
