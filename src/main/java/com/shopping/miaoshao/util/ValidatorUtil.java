package com.shopping.miaoshao.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class ValidatorUtil {

    /**
     * 手机号码正则表达式
     * */
    private static final Pattern mobile_pattern = Pattern.compile("^((/(/d{3}/))|(/d{3}/-))?13[0-9]/d{8}|15[89]/d{8}");

    /**
     * 判断是否为电话号码
     * @param phonenum 传入电话号码参数
     * */
    public static boolean isMobile(String phonenum){
        if (StringUtils.isEmpty(phonenum)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(phonenum);
        return m.matches();
    }

}
