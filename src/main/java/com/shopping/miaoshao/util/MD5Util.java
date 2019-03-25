package com.shopping.miaoshao.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author dongcheng
 * create date 2019/3/21
 **/
public class MD5Util {

    /**
     * 盐值常量
     * */
    private static final String salt = "thankyou";

    /**
     * 将明文转化加密
     * @param sourcestring 来源字符串
     * */
    public static String md5(String sourcestring){
        return DigestUtils.md2Hex(sourcestring);
    }

    /**
     * 表单提交密码加密
     * @param inputpass 传入密码
     * */
    public static String inputPassToFormPass(String inputpass){
        String password = "" + salt.charAt(1) + salt.charAt(3) + inputpass + salt.charAt(5) + salt.charAt(0);
        return md5(password);
    }

    /**
     * 表单传入数据库加密密码加密
     * @param inputpass 传入密码
     * @param salt 传入盐值
     * */
    public static String formPassToDBPass(String inputpass,String salt){
        String password = "" + salt.charAt(1) + salt.charAt(3) + inputpass + salt.charAt(5) + salt.charAt(0);
        return md5(password);
    }

    public static String inputPassToDbPass(String inputpass, String saltDB){
        String formpass = inputPassToFormPass(inputpass);
        String dbpass = formPassToDBPass(formpass,saltDB);
        return dbpass;
    }

}
