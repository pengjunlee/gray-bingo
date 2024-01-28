package com.bingo.starter.validation.caseChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TODO
 *
 * @author pjli
 * @date 2021/1/4
 * @since 1.0.0
 **/
public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    /**
     * 初始化获取注解中的值
     */
    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    /**
     * 校验
     */
    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }

        boolean isValid;
        if (caseMode == CaseMode.UPPER) {
            isValid = object.equals(object.toUpperCase());
        } else {
            isValid = object.equals(object.toLowerCase());
        }

        if (!isValid) {
            // 如果定义了message值,就用定义的,没有则去
            // ValidationMessages.properties中找CheckCase.message的值
            if (constraintContext.getDefaultConstraintMessageTemplate().isEmpty()) {
                constraintContext.disableDefaultConstraintViolation();
                constraintContext.buildConstraintViolationWithTemplate(
                        "{CheckCase.message}"
                ).addConstraintViolation();
            }
        }
        return isValid;
    }
}
