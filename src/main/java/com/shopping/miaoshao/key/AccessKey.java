package com.shopping.miaoshao.key;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class AccessKey extends BasePrefix {

    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, PREFIX);
    }

    public static final String PREFIX = "access";

}
