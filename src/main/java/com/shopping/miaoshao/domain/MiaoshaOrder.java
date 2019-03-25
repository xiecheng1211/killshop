package com.shopping.miaoshao.domain;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class MiaoshaOrder {

    private String id;
    private String goodsid;
    private String orderid;
    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
