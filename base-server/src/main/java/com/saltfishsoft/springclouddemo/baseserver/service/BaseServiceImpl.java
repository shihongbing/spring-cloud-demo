package com.saltfishsoft.springclouddemo.baseserver.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saltfishsoft.springclouddemo.baseserver.holder.RunTimeUserHolder;
import com.saltfishsoft.springclouddemo.baseserver.model.RunTimeUser;
import com.saltfishsoft.springclouddemo.baseserver.repository.IBaseRepository;
import com.saltfishsoft.springclouddemo.baseserver.util.BusinessUtils;
import com.saltfishsoft.springclouddemo.common.constant.LogicDelete;
import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Shihongbing on 2020/7/24.
 */
@Slf4j
@CacheConfig(cacheNames = "caffeineCacheManager")
public abstract class BaseServiceImpl<T extends BusinessObject,ID extends Serializable,R extends IBaseRepository<T,ID>> implements BaseService<T,ID,R> {

    @Autowired
    @PersistenceContext
    protected EntityManager entityManager;

    protected JPAQueryFactory queryFactory;
    @Autowired
    protected RunTimeUserHolder runTimeUserHolder;
    protected R baseRepository;

    @Autowired
    public void setBaseRepository(R baseRepository) {
        this.baseRepository = baseRepository;
    }

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public R getRepository() {
        return baseRepository;
    }

    @Override
    //@CachePut(key = "#entity.id")
    @Transactional
    public T add(T entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setModifyTime(now);
        entity.setStatus(LogicDelete.NORMAL.code());
        if(StringUtils.isBlank(entity.getCreateUser())){
            RunTimeUser runTimeUser = runTimeUserHolder.getCurrentUser();
            if(runTimeUser!=null){
                entity.setCreateUser(runTimeUser.getAccount());
                entity.setModifyUser(runTimeUser.getAccount());
            }else{
                entity.setCreateUser("anonymous");
                entity.setModifyUser("anonymous");
            }
        }
        baseRepository.save(entity);
        return entity;
    }

    @Override
    //@CachePut(key = "#entity.id")
    @Transactional
    public T modify(T entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setModifyTime(now);
        if(StringUtils.isBlank(entity.getModifyUser())){
            RunTimeUser runTimeUser = runTimeUserHolder.getCurrentUser();
            if(runTimeUser!=null){
                entity.setModifyUser(runTimeUser.getAccount());
            }else{
                entity.setModifyUser("anonymous");
            }
        }
        baseRepository.saveAndFlush(entity);
        return entity;
    }

    @Override
    @Cacheable(key = "#id")
    public T get(ID id) {
        return baseRepository.findOne(id);
    }

    @Override
    @CacheEvict(key = "#id")
    public void delete(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    @CacheEvict(key = "#entity.id")
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    /**
     * 逻辑删除
     * @param entity
     */
    @Override
    public void logicDelete(T entity){
        entity.setStatus(LogicDelete.LOGICDELETED.code());
        this.modify(entity);
    }

    @Override
    public boolean exists(ID id) {
        return baseRepository.existsById(id);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return baseRepository.findAll(specification);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return baseRepository.findAll(specification,pageable);
    }

}
