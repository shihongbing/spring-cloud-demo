package com.saltfishsoft.springclouddemo.accserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * 用户组织机构
 */
@Entity
@Table(name="E_USER_GROUP")
@DynamicInsert(true)
@DynamicUpdate(true)
@Setter
@Getter
public class UserGroup extends BusinessObject{

    private String groupName;

    private String groupCode;

    /**
     * 父级组织机构
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private UserGroup parent;

    /**
     * 子组织机构
     */
    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "parent",fetch = FetchType.LAZY)
    @OrderBy("displayOrder")
    @JsonIgnoreProperties({"parent","children"})
    private Set<UserGroup> children;


    /**
     * 备注
     */
    private String remarks;

    @ManyToMany(mappedBy = "userGroups", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<User> users;

}
