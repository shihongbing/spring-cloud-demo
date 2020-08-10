package com.saltfishsoft.springclouddemo.baseserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * 把常用的数据库操作封装起来，提供给我们领域类的Reponsitory接口使用
 * NoRepositoryBean 让SpringdataJpa不要自动为它生成子类
 */
@NoRepositoryBean
public interface IBaseRepository<T,ID extends Serializable>
        extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {

    /**
     * 模糊查询不分页
     * @param example
     * @return
     */
    public List<T> findByAuto(T example);

    /**
     * 自动模糊查询并进行分页，对任意的实体对象进行查询，对象里面有几个值我们就查询几个值,当值为字符型时自动Like查询,
     * 其余类型使用自动等于查询,没有值我们就查询全部
     * @param example
     * @param pageable
     * @return
     */
    public Page<T> findByAuto(T example, Pageable pageable);


    /**
     * 自动严格相等查询返回集合
     * @param example
     * @return
     */
    public List<T> findByAutoEquals(T example);

    /**
     * 自动严格相等分页查询
     * @param example
     * @param pageable
     * @return
     */
    public Page<T> findByAutoEquals(T example, Pageable pageable);


    /**
     * sql查询
     * @param sql
     * @return 返回数组对象
     */
    List<Object[]> listBySQL(String sql);

    /**
     * map参数的原始sql查询
     * @param sql
     * @param paramMap
     * @return
     */
    List<Object[]> listBySQL(String sql, Map<String, Object> paramMap);

    /**
     * 数组参数的原始sql查询
     * @param sql
     * @param values
     * @return
     */
    List<Object[]> listBySQL(String sql, Object... values);

    /**
     * 不带参数的原生jpql查询
     * @param jpql
     * @return
     */
    List<T> findByJpql(String jpql);

    /**
     * 带map参数的原生jpql查询
     * @param jpql
     * @param paramMap
     * @return
     */
    List<T> findByJpql(String jpql, Map<String, Object> paramMap);

    /**
     * 带数组参数的原生jpql查询
     * @param jpql
     * @param values
     * @return
     */
    List<T> findByJpql(String jpql, Object... values);

    /**
     * 原生jpql更新操作
     * @param jpql
     * @return
     */
    int executeUpdate(String jpql);

    /**
     * 带参数的原生jpql更新操作
     * @param jpql
     * @param paramMap
     * @return
     */
    int executeUpdate(String jpql, Map<String, Object> paramMap);


    Integer countByJpql(String jpql);
    Integer countByJpql(String jpql, Object... values);


    Integer countBySql(String sql);
    Integer countBySql(String sql, Object... values);
    Integer countBySql(String sql, Map<String, Object> paramMap);

    T findOne(ID id);

    List<T> findByPropertyEquals(String propertyName, Object value);

    Optional<T> findUniqueByProperty(String propertyName, Object value);

    Page<T> pageQuery(T example, Pageable pageable);

    Page<T> pageQuery(Specification<T> specification, Pageable pageable);




}
