package com.saltfishsoft.springclouddemo.common.model.base;

import com.saltfishsoft.springclouddemo.common.annotation.QueryCondition;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shihongbing on 2020/6/30.
 */
@Setter
@Getter
public abstract class BaseQuery<T> {
    public static Integer DEFAULT_PAGE_QUERY_SIZE = 20;
    public static Integer DEFAULT_PAGE_QUERY_PAGE_NO = 0;

    protected int page;
    protected int size;
    protected String sort;
    protected String direction;
    protected String sortProperties;

    /**
     * 多个排序字段用分号隔开
     * @return
     */
    public String[] getProperties() {
        if(StringUtils.isBlank(sortProperties)){
            return null;
        }
        String [] array = sortProperties.split(";");
        return array;
    }

    public long getOffset(){
        return page*size;
    }

    public PageRequest buildPageRequest(){
        if(this.getPage() == 0){
            this.setPage(DEFAULT_PAGE_QUERY_PAGE_NO);
        }
        if(this.getSize() == 0){
            this.setSize(DEFAULT_PAGE_QUERY_SIZE);
        }
        if(Sort.Direction.DESC.name().equalsIgnoreCase(this.getDirection())){
            //降序
            Sort sort =  Sort.by(Sort.Direction.DESC,this.getProperties());
            return PageRequest.of(this.getPage(),this.getSize(),sort);
        }
        if(Sort.Direction.ASC.name().equalsIgnoreCase(this.getDirection())){
            //升序
            Sort sort = Sort.by(Sort.Direction.ASC,this.getProperties());
            return PageRequest.of(this.getPage(),this.getSize(),sort);
        }
        return PageRequest.of(this.getPage(),this.getSize());
    }

    /**
     * 将查询转换成Specification
     * @return
     */
    public abstract Specification<T> toSpec();


    //动态查询and连接
    protected Specification<T> toSpecWithAnd() {
        return this.toSpecWithLogicType("and");
    }

    //动态查询or连接
    protected Specification<T> toSpecWithOr() {
        return this.toSpecWithLogicType("or");
    }


    private Specification<T> toSpecWithLogicType(String logicType) {
        BaseQuery outerThis = this;
        return (root, criteriaQuery, cb) -> {
            Class clazz = outerThis.getClass();
            //获取查询类Query的所有字段,包括父类字段
            List<Field> fields = getAllFieldsWithRoot(clazz);
            List<Predicate> predicates = new ArrayList<>(fields.size());
            for (Field field : fields) {
                //获取字段上的@QueryWord注解
                QueryCondition qw = field.getAnnotation(QueryCondition.class);
                if (qw == null)
                    continue;

                // 获取字段名
                String column = qw.column();
                //如果主注解上colume为默认值"",则以field为准
                if (column.equals(""))
                    column = field.getName();

                field.setAccessible(true);

                try {

                    // nullable
                    Object value = field.get(outerThis);
                    //如果值为null,注解未标注nullable,跳过
                    if (value == null && !qw.nullable())
                        continue;

                    // can be empty
                    if (value != null && String.class.isAssignableFrom(value.getClass())) {
                        String s = (String) value;
                        //如果值为"",且注解未标注emptyable,跳过
                        if (s.equals("") && !qw.emptyable())
                            continue;
                    }

                    //通过注解上func属性,构建路径表达式
                    Path path = root.get(column);
                    switch (qw.func()) {
                        case equal:
                            predicates.add(cb.equal(path, value));
                            break;
                        case like:
                            predicates.add(cb.like(path, "%" + value + "%"));
                            break;
                        case gt:
                            predicates.add(cb.gt(path, (Number) value));
                            break;
                        case lt:
                            predicates.add(cb.lt(path, (Number) value));
                            break;
                        case ge:
                            predicates.add(cb.ge(path, (Number) value));
                            break;
                        case le:
                            predicates.add(cb.le(path, (Number) value));
                            break;
                        case notEqual:
                            predicates.add(cb.notEqual(path, value));
                            break;
                        case notLike:
                            predicates.add(cb.notLike(path, "%" + value + "%"));
                            break;
                        case greaterThan:
                            predicates.add(cb.greaterThan(path, (Comparable) value));
                            break;
                        case greaterThanOrEqualTo:
                            predicates.add(cb.greaterThanOrEqualTo(path, (Comparable) value));
                            break;
                        case lessThan:
                            predicates.add(cb.lessThan(path, (Comparable) value));
                            break;
                        case lessThanOrEqualTo:
                            predicates.add(cb.lessThanOrEqualTo(path, (Comparable) value));
                            break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            Predicate p = null;
            if (logicType == null || logicType.equals("") || logicType.equals("and")) {
                p = cb.and(predicates.toArray(new Predicate[predicates.size()]));//and连接
            } else if (logicType.equals("or")) {
                p = cb.or(predicates.toArray(new Predicate[predicates.size()]));//or连接
            }
            return p;
        };
    }

    //获取类clazz的所有Field，包括其父类的Field
    private List<Field> getAllFieldsWithRoot(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Field[] dFields = clazz.getDeclaredFields();//获取本类所有字段
        if (null != dFields && dFields.length > 0)
            fieldList.addAll(Arrays.asList(dFields));

        // 若父类是Object，则直接返回当前Field列表
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) return Arrays.asList(dFields);

        // 递归查询父类的field列表
        List<Field> superFields = getAllFieldsWithRoot(superClass);

        if (null != superFields && !superFields.isEmpty()) {
            superFields.stream().
                    filter(field -> !fieldList.contains(field)).//不重复字段
                    forEach(field -> fieldList.add(field));
        }
        return fieldList;
    }
}
