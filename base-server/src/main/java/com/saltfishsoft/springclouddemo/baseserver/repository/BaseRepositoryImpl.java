package com.saltfishsoft.springclouddemo.baseserver.repository;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库操作基类服务
 * @param <T> 实体对象
 * @param <ID> 主键类型
 */
public class BaseRepositoryImpl <T,ID extends Serializable> extends SimpleJpaRepository<T,ID>
        implements IBaseRepository<T,ID>{
    protected static final Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);
    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass,EntityManager entityManager){
        super(domainClass,entityManager);
        this.entityManager = entityManager;
    }

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityManager = em;
    }

    @Override
    public List<T> findByAuto(T example){
        return findAll(CustomerSpecs.byAuto(entityManager,example));
    }

    @Override
    public Page<T> findByAuto(T example, Pageable pageable) {
        return findAll(CustomerSpecs.byAuto(entityManager,example),pageable);
    }

    @Override
    public List<T> findByAutoEquals(T example) {
        return findAll(CustomerSpecs.byAutoEquals(entityManager,example));
    }

    @Override
    public Page<T> findByAutoEquals(T example, Pageable pageable){
        return findAll(CustomerSpecs.byAutoEquals(entityManager,example),pageable);
    }

    @Override
    public T findOne(ID id){
        Optional<T> optional = super.findById(id);
        if(Optional.empty().equals(optional)){
            return null;
        }else{
            return optional.get();
        }
    }

    @Override
    public List<T> findByPropertyEquals(String propertyName, Object value) {
        return findAll(CustomerSpecs.byProperty(propertyName,value));
    }

    @Override
    public Optional<T> findUniqueByProperty(String propertyName,Object value){
        Specification<T> s = CustomerSpecs.byProperty(propertyName,value);
        return findOne(s);
    }

    @Override
    public List<Object[]> listBySQL(String sql){
        return entityManager.createNativeQuery(sql).getResultList();
    }

    @Override
    public List<Object[]> listBySQL(String sql, Map<String, Object> paramMap) {
        Query query = entityManager.createNativeQuery(sql);
        this.setQueryParameter(query,paramMap);
        return query.getResultList();
    }

    @Override
    public List<Object[]> listBySQL(String sql, Object... values) {
        Query query = entityManager.createNativeQuery(sql);
        for(int i = 0;i<values.length;i++){
            query.setParameter(i,values[i]);
        }
        return query.getResultList();
    }


    @Override
    public List<T> findByJpql(String jpql){
        return entityManager.createQuery(jpql).getResultList();
    }


    @Override
    public List<T> findByJpql(String jpql, Map<String, Object> paramMap) {
        Query query = entityManager.createQuery(jpql);
        this.setQueryParameter(query,paramMap);
        return query.getResultList();
    }

    @Override
    public List<T> findByJpql(String jpql, Object... values){
        Query query = entityManager.createQuery(jpql);
        this.setQueryParameter(query,values);
        return query.getResultList();
    }


    @Override
    @Transactional
    public int executeUpdate(String jpql){
        Query query = entityManager.createQuery(jpql);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int executeUpdate(String jpql, Map<String, Object> paramMap) {
        Query query = entityManager.createQuery(jpql);
        this.setQueryParameter(query,paramMap);
        return query.executeUpdate();
    }

    @Override
    public Integer countByJpql(String jpql) {
        Query query = entityManager.createQuery(jpql);
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer countByJpql(String jpql, Object... values){
        Query query = entityManager.createQuery(jpql);
        this.setQueryParameter(query,values);
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer countBySql(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer countBySql(String sql, Object... values) {
        Query query = entityManager.createNativeQuery(sql);
        this.setQueryParameter(query,values);
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Integer countBySql(String sql, Map<String, Object> paramMap)  {
        Query query = entityManager.createNativeQuery(sql);
        this.setQueryParameter(query,paramMap);
        Integer total = Integer.parseInt(query.getSingleResult().toString());
        return total;
    }

    @Override
    public Page<T> pageQuery(T example, Pageable pageable) {
        return this.findByAuto(example,pageable);
    }

    @Override
    public Page<T> pageQuery(Specification<T> specification, Pageable pageable)  {
        return this.findAll(specification,pageable);
    }

    private Query setQueryParameter(Query query, Map<String, Object> paramMap){
        if(MapUtils.isNotEmpty(paramMap)){
            for(Map.Entry<String,Object> entry : paramMap.entrySet()){
                String key = entry.getKey();
                Object object = entry.getValue();
                query.setParameter(key,object);
            }
        }else{
            logger.error("参数为空");
        }
        return query;
    }

    private Query setQueryParameter(Query query, Object... values){
        for(int i = 0;i<values.length;i++){
            query.setParameter(i,values[i]);
        }
        return query;
    }



}
