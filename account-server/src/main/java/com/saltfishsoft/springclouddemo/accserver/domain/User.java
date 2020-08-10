package com.saltfishsoft.springclouddemo.accserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltfishsoft.springclouddemo.common.annotation.FieldDesc;
import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import com.saltfishsoft.springclouddemo.common.valid.ListValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 */
@Entity
@Table(name="E_USER",uniqueConstraints = {@UniqueConstraint(columnNames="account")})
@DynamicInsert(true)
@DynamicUpdate(true)
@Setter
@Getter
public class User extends BusinessObject {

    @FieldDesc("用户帐号")
    @NotBlank(groups = Add.class)
    private String account;

    @Pattern(regexp = BusinessConstants.USER_PWD_REG, message = "密码必须为8~16个字母和数字组合",groups = {Add.class,Modify.class})
    @NotBlank(groups = Add.class)
    private String password;

    @FieldDesc("用户姓名")
    @NotBlank(groups = Add.class)
    private String userName;

    @FieldDesc("手机号")
    @NotBlank(groups = Add.class,message = "")
    @Pattern(regexp = BusinessConstants.PHONE_REG, message = "手机号格式不对",groups = {Add.class,Modify.class})
    private String phone;

    @FieldDesc("邮箱")
    @Email(groups = {Add.class,Modify.class})
    private String email;

    @FieldDesc("性别")
    @ListValue(message = "性别取值范围{1:男,2:女}" , vals = {"1","2"},groups = {Add.class,Modify.class})
    private String gender;

    @FieldDesc("身份证号")
    @NotBlank(groups = {Add.class})
    private String idCardNo;

    @FieldDesc("员工编号")
    @NotBlank(groups = Add.class)
    private String employeeNo;

    @FieldDesc("用户类型")
    private String userType;

    @FieldDesc("生日")
    private String birthday;

    @FieldDesc("地址")
    private String address;


    @FieldDesc("用户所属角色")
    /**用户角色*/
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "E_USER_ROLE_RELATION",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnoreProperties({"id","createUser","createTime","modifyUser","modifyTime","status","menus","users"})
    private Set<Role> roles = new HashSet<>();

    @FieldDesc("用户组织机构")
    /**用户组织机构信息*/
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "E_USER_GROUP_RELATION",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @JsonIgnoreProperties({"id","createUser","createTime","modifyUser","modifyTime","status","users","parent","children"})
    private Set<UserGroup> userGroups = new HashSet<>();


    public User(){

    }

    public User(String account,String userName){

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append("id:").append(id).append(",")
                .append("account:").append(account).append(",")
                .append("name:").append(userName)
                .append("]");
        return sb.toString();
    }


}
