package com.saltfishsoft.springclouddemo.common.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Shihongbing on 2020/6/19.
 */
@Setter
@Getter
public class BaseDto implements Serializable {
    protected String id;
    protected String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected LocalDateTime createTime;
    protected String modifyUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected LocalDateTime modifyTime;
    protected String status;


}
