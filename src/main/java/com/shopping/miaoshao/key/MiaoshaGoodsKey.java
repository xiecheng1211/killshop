package com.shopping.miaoshao.key;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class MiaoshaGoodsKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 *24 * 3;

    public MiaoshaGoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaGoodsKey goodsList = new MiaoshaGoodsKey(0,"MG");
}
