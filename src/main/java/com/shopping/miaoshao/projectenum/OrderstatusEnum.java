package com.shopping.miaoshao.projectenum;

/**
 * @author dongcheng
 * create date 2019/3/23
 **/
public enum OrderstatusEnum {

    CREATE_NOPAY(0,"创建未支付"),
    PAYED(1,"已支付"),
    SENDGOODS(2,"已发货"),
    ACCEPTGOODS(3,"已收货"),
    REFUND(4,"已退款"),
    COMPLETE(5,"已完成");

    private final Integer code;
    private final String msg;

    OrderstatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
