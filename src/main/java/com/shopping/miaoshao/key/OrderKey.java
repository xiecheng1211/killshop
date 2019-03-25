package com.shopping.miaoshao.key;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

}
