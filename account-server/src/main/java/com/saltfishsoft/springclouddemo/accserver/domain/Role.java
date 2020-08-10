package com.saltfishsoft.springclouddemo.accserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltfishsoft.springclouddemo.common.annotation.FieldDesc;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 *系统角色表
 */
@Entity
@Table(name="E_ROLE")
@DynamicInsert(true)
@DynamicUpdate(true)
@Setter
@Getter
public class Role extends BusinessObject {

    /**
     * 角色名称
     */
    @FieldDesc("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @FieldDesc("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色备注
     */
    private String remarks;


    /**
     * 角色拥有的菜单权限
     * 双向对多对，原理是双向主控，删除任何一方的数据都会对中间表的关联数据进行删除。
     *
     */
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "E_ROLE_MENU_RELATION",
            joinColumns = {@JoinColumn(name = "roleId",referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "menuId",referencedColumnName="id")})
    @JsonIgnoreProperties({"parent","children"})
    @JsonIgnore
    private List<Menu> menus;


    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    public Role(){

    }

}
