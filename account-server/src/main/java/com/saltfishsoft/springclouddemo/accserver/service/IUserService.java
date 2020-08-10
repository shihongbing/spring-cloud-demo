package com.saltfishsoft.springclouddemo.accserver.service;

import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.accserver.repository.UserRepository;
import com.saltfishsoft.springclouddemo.baseserver.service.BaseService;

/**
 * Created by Shihongbing on 2020/7/1.
 */
public interface IUserService extends BaseService<User,String,UserRepository> {

    /**
     * 用户角色授权
     * @param userId
     * @param roleIds
     * @return
     */
    User authorizeRoles(String userId, String[] roleIds);

    User add(User user);

}
