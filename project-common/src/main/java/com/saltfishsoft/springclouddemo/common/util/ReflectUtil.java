package com.saltfishsoft.springclouddemo.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Shihongbing on 2018/7/30.
 */
public class ReflectUtil {

    public ReflectUtil() {
    }

    /**
     * 获取obj对象fileName的值
     * @param obj 对象
     * @param fieldName 属性名
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if(field != null) {
            field.setAccessible(true);

            try {
                result = field.get(obj);
            } catch (IllegalArgumentException var5) {
                var5.printStackTrace();
            } catch (IllegalAccessException var6) {
                var6.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 通过对象的field属性名称获取对象属性
     * @param obj
     * @param fieldName
     * @return
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        Class clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return field;
    }

    /**
     *设置对象filedValue属性的值
     * @param obj 对象
     * @param fieldName  名称
     * @param fieldValue 值
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = getField(obj, fieldName);
        if(field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException var5) {
                var5.printStackTrace();
            } catch (IllegalAccessException var6) {
                var6.printStackTrace();
            }
        }
    }

    /**
     * @param source 提交的实体数据源
     * @return 将数据源中空的字段取出
     */
    public static String[] getValueNullProperties(Object source) {
        BeanWrapper srcBean = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value == null) noEmptyName.add(p.getName());
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }

    /**
     * 将目标源中空的字段过滤，并将不为null的字段值,赋值到数据库中查出的数据上
     *
     * @param source 提交的实体数据源
     * @param target 用id从数据库中查出来的目标源
     */
    public static void copyProperties(Object source,Object target) {
        //copyProperties 将source的属性值复制到target中
        BeanUtils.copyProperties(source, target, getValueNullProperties(source));
    }
}
