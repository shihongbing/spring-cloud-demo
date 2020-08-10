package com.saltfishsoft.springclouddemo.common.model.result;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量操作返回结果格式
 */
@Setter
@Getter
public class BatchResult<T> extends RequestResult {
    private static final long serialVersionUID = 1L;

    private List<RequestResult<T>> successList = new ArrayList<RequestResult<T>>();

    private List<RequestResult<T>> failedList = new ArrayList<RequestResult<T>>();


    public BatchResult(boolean success, Integer code, String message){
        super(success,code,message);
    }

    public void addSuccess(RequestResult<T> success) {
        this.successList.add(success);
    }

    public void addFailed(RequestResult<T> failed) {
        this.failedList.add(failed);
    }

}
