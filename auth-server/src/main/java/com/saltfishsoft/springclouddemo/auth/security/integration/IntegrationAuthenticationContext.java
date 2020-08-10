package com.saltfishsoft.springclouddemo.auth.security.integration;


import com.saltfishsoft.springclouddemo.auth.security.integration.authenticator.IntegrationAuthentication;

/**
 * Created by Shihongbing on 2019/10/15.
 */
public class IntegrationAuthenticationContext {

    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication){
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }

}
