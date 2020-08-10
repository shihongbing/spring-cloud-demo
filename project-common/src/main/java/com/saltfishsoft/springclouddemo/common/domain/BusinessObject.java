package com.saltfishsoft.springclouddemo.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saltfishsoft.springclouddemo.common.annotation.FieldDesc;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by shihongbing on 2019/9/20.
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BusinessObject implements Serializable {

    protected static final Logger logger = LoggerFactory.getLogger(BusinessObject.class);
    private static final long serialVersionUID = -7292033562091745796L;
    /**
     * @GenericGenerator注解是hibernate所提供的自定义主键生成策略生成器，
     * 由@GenericGenerator实现多定义的策略。所以，它要配合@GeneratedValue一起使用，
     * 并且@GeneratedValue注解中的”generator”
     * 属性要与@GenericGenerator注解中name属性一致，strategy属性表示hibernate的主键生成策略
     * uuid.hex is deprecated
     */
    @Id
    @Column(length = 64)
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator" )
    @NotNull(message = "id不能为空",groups = Modify.class)
    @Null(groups = Add.class)
    @FieldDesc("主键")
    protected String id;

    @FieldDesc("创建人")
    protected String createUser;

    @FieldDesc("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected LocalDateTime createTime;

    @FieldDesc("修改人")
    protected String modifyUser;

    @FieldDesc("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected LocalDateTime modifyTime;

    @FieldDesc("状态")
    protected String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessObject)) return false;

        BusinessObject baseBo = (BusinessObject) o;

        return id != null ? id.equals(baseBo.id) : baseBo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static void main(String args[]){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }


    public interface Modify{};
    public interface Add{}
}