package com.saltfishsoft.springclouddemo.baseserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Shihongbing on 2020/6/30.
 */
@Setter
@Getter
public class RunTimeUser {

    private String id;
    private String account;
    private String username;
    private String password;
    private List<String> roles;

    public RunTimeUser(){

    }

    public RunTimeUser(String id){
        this.id = this.id;
    }

    public RunTimeUser(String id,String account){
        this.id = id;
        this.account = account;
    }

    public RunTimeUser(String id, String account, String username, List<String> roles) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.roles = roles;
    }
}
