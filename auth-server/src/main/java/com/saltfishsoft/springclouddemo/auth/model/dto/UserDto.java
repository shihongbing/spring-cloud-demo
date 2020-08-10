package com.saltfishsoft.springclouddemo.auth.model.dto;
import com.saltfishsoft.springclouddemo.common.model.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Shihongbing on 2020/7/17.
 */
@Setter
@Getter
public class UserDto extends BaseDto{

    private String account;

    private String password;

    private String userName;

    private String phone;

    private String email;

    private String gender;

    private String idCardNo;

    private String employeeNo;

    private String userType;

    private String birthday;

    private String address;

    private Set<RoleDto> roles;

    public UserDto(){

    }

}
