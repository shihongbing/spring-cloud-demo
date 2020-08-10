package com.saltfishsoft.springclouddemo.baseserver.service;

import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shihongbing on 2020/7/1.
 */
public interface BaseService<T extends BusinessObject,ID extends Serializable,R extends IBaseRepository<T,ID>> {


    /**
     * 获取Repository
     * @return
     */
    R getRepository();

    T add(T t);

    T modify(T t);

    T get(ID id);

    void delete(ID id);

    void delete(T t);

    void logicDelete(T entity);

    boolean exists(ID id);

    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

}
