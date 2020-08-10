package com.saltfishsoft.springclouddemo.accserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 系统菜单权限
 */
@Entity
@Table(name="E_MENU")
@DynamicInsert(true)
@DynamicUpdate(true)
@Getter
@Setter
public class Menu extends BusinessObject {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 对应后台地址
     */
    private String actionUrl;


    /**
     * 上级权限
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Menu parent;

    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "parent",fetch = FetchType.LAZY)
    @OrderBy("displayOrder")
    @JsonIgnoreProperties({"parent","children"})
    private List<Menu> children;

    /**
     * 排序
     */
    private Integer displayOrder;


    /**
     * 备注
     */
    private String remarks;



    public Menu(){

    }

    @Override
    public String toString(){
        return "[menuName:"+menuName+"id:"+id+"]";
    }

}
