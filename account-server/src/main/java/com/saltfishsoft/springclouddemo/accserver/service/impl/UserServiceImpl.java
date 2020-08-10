package com.saltfishsoft.springclouddemo.accserver.service.impl;

import com.google.common.base.Preconditions;
import com.saltfishsoft.springclouddemo.accserver.domain.Role;
import com.saltfishsoft.springclouddemo.accserver.domain.User;
import com.saltfishsoft.springclouddemo.accserver.domain.UserGroup;
import com.saltfishsoft.springclouddemo.accserver.repository.RoleRepository;
import com.saltfishsoft.springclouddemo.accserver.repository.UserGroupRepository;
import com.saltfishsoft.springclouddemo.accserver.repository.UserRepository;
import com.saltfishsoft.springclouddemo.accserver.service.IUserService;
import com.saltfishsoft.springclouddemo.baseserver.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.saltfishsoft.springclouddemo.common.constant.BusinessConstants.USER_DEFAULT_PWD;

/**
 * Created by Shihongbing on 2020/7/1.
 */
@Service("userService")
@Transactional(rollbackFor=Exception.class)
@Slf4j
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserServiceImpl extends BaseServiceImpl<User,String,UserRepository> implements IUserService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User authorizeRoles(String userId, String[] roleIds) {
        User user = super.get(userId);
        Preconditions.checkNotNull(user,"user not find");
        List<Role> list = roleRepository.findByIdIn(Arrays.asList(roleIds));
        Set<Role> roles = new HashSet<Role>(list);
        user.setRoles(roles);
        super.modify(user);
        return user;
    }

    //用户添加
    @Override
    public User add(User user) {
        Optional<User> optional = this.baseRepository.findUniqueByProperty("account",user.getAccount());
        Preconditions.checkArgument(!optional.isPresent(),"帐号重名!");
        //角色处理
        if(CollectionUtils.isNotEmpty(user.getRoles())){
            Set<Role> roles = new HashSet<>();
            for(Role role : user.getRoles()){
                role = roleRepository.findOne(role.getId());
                if(role!=null){
                    roles.add(role);
                }
            }
            if(CollectionUtils.isNotEmpty(roles)){
                user.setRoles(roles);
            }
        }
        //组织机构处理
        if(CollectionUtils.isNotEmpty(user.getUserGroups())){
            Set<UserGroup> groups = new HashSet<>();
            for(UserGroup group : user.getUserGroups()){
                group = userGroupRepository.findOne(group.getId());
                if(group!=null){
                    groups.add(group);
                }
            }
            if(CollectionUtils.isNotEmpty(groups)){
                user.setUserGroups(groups);
            }
        }
        //密码加密处理
        if(StringUtils.isNotBlank(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            user.setPassword(passwordEncoder.encode(USER_DEFAULT_PWD));
        }
        super.add(user);
        return user;
    }
}
