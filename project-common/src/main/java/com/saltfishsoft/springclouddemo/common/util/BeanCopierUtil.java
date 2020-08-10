package com.saltfishsoft.springclouddemo.common.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Shihongbing on 2019/10/25.
 */
public class BeanCopierUtil {
    /**
     * BeanCopier的缓存
     */
    static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<String, BeanCopier>();


    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = genKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, true);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    public static void copy(Object target,Object source) {
        copy(target,source,false);
    }

    /**
     * BeanCopier的copy
     * @param source 源文件的
     * @param target 目标文件
     */
    public static void copy(Object target,Object source,boolean cover) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source,target, (value, t, context)->{
            if(!cover && value == null) {
                return BeanMap.create(target).get(getPropertyName(String.valueOf(context)));
            }
            return value;
        });
    }

    private static String getPropertyName(String methodName) {//setAge ---> age
        char[] newChar = new char[methodName.length() - 3];
        System.arraycopy(methodName.toCharArray(), 3, newChar, 0, methodName.length() - 3);
        newChar[0] = Character.toLowerCase(newChar[0]);
        return String.valueOf(newChar);
    }

    /**
     * 生成key
     * @param srcClazz 源文件的class
     * @param tgtClazz 目标文件的class
     * @return string
     */
    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }
}
