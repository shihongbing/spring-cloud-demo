package com.saltfishsoft.springclouddemo.auth.model.dto;

import com.saltfishsoft.springclouddemo.common.constant.LogicDelete;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Shihongbing on 2020/7/17.
 */
@Setter
@Getter
public class SecurityUser implements UserDetails{

    /**
     * ID
     */
    private String id;

    /**
     * 登录名
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser() {

    }
    public SecurityUser(UserDto userDTO) {
        this.setId(userDTO.getId());
        this.setAccount(userDTO.getAccount());
        this.setUserName(userDTO.getUserName());
        this.setPassword(userDTO.getPassword());
        this.setEnabled(LogicDelete.NORMAL.code().equals(userDTO.getStatus()));
        if (!CollectionUtils.isEmpty(userDTO.getRoles())) {
            authorities = new ArrayList<>();
            userDTO.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getRoleCode())));
        }else{
            authorities.add(new SimpleGrantedAuthority("role_anonymous"));
        }
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
