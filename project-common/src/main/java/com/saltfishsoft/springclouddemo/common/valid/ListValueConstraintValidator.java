package com.saltfishsoft.springclouddemo.common.valid;

/**
 * Created by Shihongbing on 2020/6/19.
 */
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,String> {

    private Set<String> set = new HashSet<>();
    /**
     * 初始化方法
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {

        String[] vals = constraintAnnotation.vals();
        for (String val : vals) {
            set.add(val);
        }

    }

    /**
     * 判断是否校验成功
     *
     * @param value 需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return set.contains(value);
    }
}
