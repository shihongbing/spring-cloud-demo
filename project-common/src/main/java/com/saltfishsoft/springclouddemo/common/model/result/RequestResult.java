package com.saltfishsoft.springclouddemo.common.model.result;

import com.saltfishsoft.springclouddemo.common.constant.BusinessConstants;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * http请求返回结果
 */
@Setter
@Getter
@Slf4j
public class RequestResult<T> implements Serializable {

    private static final long serialVersionUID = 3104436221012436214L;

    private boolean success = true;

    private T data;

    //错误编号
    private Integer code = BusinessConstants.SUCCESS_CODE;

    //错误信息
    private String message =  BusinessConstants.SUCCESS_MESSAGE;

    public RequestResult(){

    }

    public RequestResult(T data){
        this.data = data;
    }

    public RequestResult(Integer code,String message){
        this.success = false;
        this.code = code;
        this.message = message;
    }

    public RequestResult(Integer code,String message,T data){
        this.success = false;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RequestResult(boolean success,Integer code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }


    public static RequestResult build(Object data){
        return new RequestResult(data);
    }

    public static <S extends BusinessObject> RequestResult buildPageResult(Page<S> page){
        Pagination<S> pagination = new Pagination<>();
        pagination.setNumber(page.getNumber());
        pagination.setSize(page.getSize());
        pagination.setContent(page.getContent());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalElements(page.getTotalElements());
        return new RequestResult(pagination);

    }

    public static RequestResult build(Integer code, String message) {
        return new RequestResult(code, message);
    }

    public static RequestResult build(Integer code, String message,Object data) {
        return new RequestResult(code,message,data);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestResult)) return false;
        if (!super.equals(o)) return false;

        RequestResult that = (RequestResult) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
