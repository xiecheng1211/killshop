package com.shopping.miaoshao.validator;

import com.shopping.miaoshao.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    /**
     * 默认不需要填写
     * */
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    /**
     * 校验手机号码正确性
     * @param phonenumber 手机号码
     * @param constraintValidatorContext 注解对象信息
     * */
    @Override
    public boolean isValid(String phonenumber, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isMobile(phonenumber);
        }else if (!StringUtils.isEmpty(phonenumber)){
            return true;
        }
        return false;
    }
}
