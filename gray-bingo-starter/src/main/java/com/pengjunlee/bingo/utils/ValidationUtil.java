package com.pengjunlee.bingo.utils;

import com.pengjunlee.bingo.validation.BeanValidationResult;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;


/**
 * JSR303校验工具
 *
 * @author 01402091
 * @date 2021年4月13日 上午11:54:56
 */
public class ValidationUtil {

    private ValidationUtil() {
        throw new UnsupportedOperationException("illegal operation!");
    }

    /**
     * 默认{@link Validator} 对象
     */
    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 获取原生{@link Validator} 对象
     *
     * @return {@link Validator} 对象
     */
    public static Validator getValidator() {
        return validator;
    }

    /**
     * 校验bean,返回Map，校验通过返回NULL 注意：同一KEY, 只保存1个校验信息
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link Map}
     */
    public static <T> Map<String, String> wrapMap(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> validationSet = validate(bean, groups);
        Map<String, String> errorMap = null;
        if (!CollectionUtils.isEmpty(validationSet)) {
            errorMap = new HashMap<>();
            for (ConstraintViolation<T> cv : validationSet) {
                errorMap.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
        }
        return errorMap;
    }

    /**
     * 校验bean,返回List，校验通过返回NULL
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link List}
     */
    public static <T> List<String> wrapList(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> validationSet = validate(bean, groups);
        List<String> errorList = null;
        if (!CollectionUtils.isEmpty(validationSet)) {
            errorList = new ArrayList<>();
            for (ConstraintViolation<T> cv : validationSet) {
                errorList.add(cv.getMessage());
            }
        }
        return errorList;
    }

    /**
     * 校验bean,返回Set,校验通过返回NULL
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link List}
     */
    public static <T> Set<String> wrapSet(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> validationSet = validate(bean, groups);
        Set<String> errorSet = null;
        if (!CollectionUtils.isEmpty(validationSet)) {
            errorSet = new HashSet<>();
            for (ConstraintViolation<T> cv : validationSet) {
                errorSet.add(cv.getMessage());
            }
        }
        return errorSet;
    }

    /**
     * 校验bean,返回String，校验通过返回NULL
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link List}
     */
    public static <T> String wrapErrorMessage(T bean, Class<?>... groups) {
        Set<String> errorSet = wrapSet(bean, groups);
        if (Objects.isNull(errorSet)) {
            return null;
        }
        return StringUtil.join(errorSet.toArray(), ",");
    }

    /**
     * 校验bean,返回String，校验通过返回NULL
     *
     * @param bean   Bean类型
     * @param groups 校验组
     * @return 检验不通过，只返回一个错误信息
     */
    public static <T> String wrapFirstErrorMessage(T bean, Class<?>... groups) {
        Set<String> errorSet = wrapSet(bean, groups);
        if (Objects.isNull(errorSet)) {
            return null;
        }
        return errorSet.stream().findFirst().get();
    }

    /**
     * 校验bean
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link BeanValidationResult}
     */
    public static <T> BeanValidationResult wrapResult(T bean, Class<?>... groups) {
        return wrapBeanValidationResult(validate(bean, groups));
    }

    /**
     * 校验bean的某一个属性
     *
     * @param <T>          bean类型
     * @param bean         bean
     * @param propertyName 属性名称
     * @param groups       验证分组
     * @return {@link BeanValidationResult}
     */
    public static <T> BeanValidationResult wrapValidateProperty(T bean, String propertyName, Class<?>... groups) {
        return wrapBeanValidationResult(validateProperty(bean, propertyName, groups));
    }

    /**
     * 校验bean
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link Set}
     */
    public static <T> Set<ConstraintViolation<T>> validate(T bean, Class<?>... groups) {
        return validator.validate(bean, groups);
    }

    /**
     * 校验bean的某一个属性
     *
     * @param <T>          Bean类型
     * @param bean         bean
     * @param propertyName 属性名称
     * @param groups       验证分组

     * @return {@link Set}
     */
    public static <T> Set<ConstraintViolation<T>> validateProperty(T bean, String propertyName, Class<?>... groups) {
        return validator.validateProperty(bean, propertyName, groups);
    }

    /**
     * 包装校验结果
     *
     * @param constraintViolations 校验结果集
     * @return {@link BeanValidationResult}
     */
    private static <T> BeanValidationResult wrapBeanValidationResult(Set<ConstraintViolation<T>> constraintViolations) {
        BeanValidationResult result = new BeanValidationResult(constraintViolations.isEmpty());
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            BeanValidationResult.ErrorMessage errorMessage = new BeanValidationResult.ErrorMessage();
            errorMessage.setPropertyName(constraintViolation.getPropertyPath().toString());
            errorMessage.setMessage(constraintViolation.getMessage());
            errorMessage.setValue(constraintViolation.getInvalidValue());
            result.addErrorMessage(errorMessage);
        }
        return result;
    }
}
