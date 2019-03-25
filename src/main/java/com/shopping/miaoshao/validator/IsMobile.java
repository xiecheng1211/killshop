package com.shopping.miaoshao.validator;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsMobile {

    boolean required() default true;

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
