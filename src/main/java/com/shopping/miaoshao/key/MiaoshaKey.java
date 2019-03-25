package com.shopping.miaoshao.key;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class MiaoshaKey extends BasePrefix {


    public static final int path = 3600 *24 * 3;

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    private MiaoshaKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");

}
