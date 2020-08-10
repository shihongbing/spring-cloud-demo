package com.saltfishsoft.springclouddemo.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by Shihongbing on 2020/7/2.
 */
@Entity
@Table(name="E_SYSTEM_LOG")
@DynamicInsert(true)
@DynamicUpdate(true)
@Setter
@Getter
public class SystemLog extends BusinessObject{

    /**
     * 操作人账号
     */
    private String account;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operTime;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 请求参数
     */
    @Column(columnDefinition="varchar(2048) COMMENT '请求参数'")
    private String params;

    /**
     * 操作内容
     */
    @Column(columnDefinition="varchar(2048) COMMENT '操作内容'")
    private String content;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 操作结果描述
     */
    private String resultDesc;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append("id:").append(id)
                .append("]");
        return sb.toString();
    }


}
