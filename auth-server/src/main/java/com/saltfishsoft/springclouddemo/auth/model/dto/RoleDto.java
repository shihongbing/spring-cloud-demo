package com.saltfishsoft.springclouddemo.auth.model.dto;

import com.saltfishsoft.springclouddemo.common.model.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Shihongbing on 2020/7/17.
 */
@Setter
@Getter
public class RoleDto extends BaseDto {

    private String roleName;

    private String roleCode;

    private String remarks;
}
