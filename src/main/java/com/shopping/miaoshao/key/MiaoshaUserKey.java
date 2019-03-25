package com.shopping.miaoshao.key;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class MiaoshaUserKey extends  BasePrefix{
    public static final int TOKEN_EXPIRE = 3600 *24 * 3;

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
}
