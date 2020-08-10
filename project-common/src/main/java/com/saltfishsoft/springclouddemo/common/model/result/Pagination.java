package com.saltfishsoft.springclouddemo.common.model.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Shihongbing on 2020/6/30.
 */
@Setter
@Getter
public class Pagination<T> {

    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<T> content;
}
