package com.saltfishsoft.springclouddemo.baseserver.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;

/**
 * Created by Administrator on 2017/6/29.
 */
public class CustomerSpecs {

    /**
     * 自动模糊查询，对任意的实体对象进行查询，对象里面有几个值我们就查询几个值,当值为字符型时自动Like查询,
     * 其余类型使用自动等于查询,没有值我们就查询全部
     * @param entityManager
     * @param example
     * @param <T>
     * @return
     */
    public static <T>Specification<T> byAuto(final EntityManager entityManager, final T example){
        final Class<T> type = (Class<T>) example.getClass();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                for(Attribute<T,?> attr : entity.getDeclaredAttributes()){
                    Object attrValue = getValue(example,attr);
                    if(attrValue !=null){
                        if(attr.getJavaType() == String.class){
                            if(!StringUtils.isEmpty(attrValue)){
                                predicates.add(cb.like(root.get(attribute(entity,attr.getName(),String.class)),pattern((String)attrValue)));
                            }
                        }
                        else{
                            predicates.add(cb.equal(root.get(attribute(entity,attr.getName(),attrValue.getClass())),attrValue));
                        }
                    }
                }
                return predicates.isEmpty() ? cb.conjunction() : cb.and(toArray(predicates,Predicate.class));
            }
        };
    }

    public static <T>Specification<T> byProperty(final String propertyName, final Object vaule){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get(propertyName),vaule));
                return predicates.isEmpty() ? cb.conjunction() : cb.and(toArray(predicates,Predicate.class));
            }
        };
    }

    public static <T>Specification<T> byAutoEquals(final EntityManager entityManager, final T example){
        final Class<T> type = (Class<T>) example.getClass();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                for(Attribute<T,?> attr : entity.getDeclaredAttributes()){
                    Object attrValue = getValue(example,attr);
                    if(attrValue !=null){
                        predicates.add(cb.equal(root.get(attribute(entity,attr.getName(),attrValue.getClass())),attrValue));
                    }
                }
                return predicates.isEmpty() ? cb.conjunction() : cb.and(toArray(predicates,Predicate.class));
            }
        };
    }

    private static <T> Object getValue(T example,Attribute<T,?> attr){
        return ReflectionUtils.getField((Field) attr.getJavaMember(),example);
    }

    private static <E,T>SingularAttribute<T,E> attribute(EntityType<T> entity, String fieldName, Class<E> fieldClass){
        return entity.getDeclaredSingularAttribute(fieldName,fieldClass);
    }

    private static String pattern(String str){
        return "%"+str+"%";
    }
}
