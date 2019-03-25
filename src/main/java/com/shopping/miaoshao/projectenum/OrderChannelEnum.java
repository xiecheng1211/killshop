package com.shopping.miaoshao.projectenum;

/**
 * @author dongcheng
 * create date 2019/3/23
 **/
public enum  OrderChannelEnum {

    PC(1,"桌面端"),
    ANDROID(2,"安卓端"),
    IOS(3,"苹果端");

    private final Integer code;
    private final String msg;

    OrderChannelEnum(Integer code,String msg) {
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
