package com.shopping.miaoshao.domain;

import java.util.Date;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class OrderInfo {

    private String id;
    private String goodsid;
    private String userid;
    private String deliveryaddrid;
    private String goodsname;
    private int goodscount;
    private double goodsprice;
    private int orderchannel;
    private int status;
    private Date createdate;
    private Date paydate;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeliveryaddrid() {
        return deliveryaddrid;
    }

    public void setDeliveryaddrid(String deliveryaddrid) {
        this.deliveryaddrid = deliveryaddrid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public int getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(int goodscount) {
        this.goodscount = goodscount;
    }

    public double getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(double goodsprice) {
        this.goodsprice = goodsprice;
    }

    public int getOrderchannel() {
        return orderchannel;
    }

    public void setOrderchannel(int orderchannel) {
        this.orderchannel = orderchannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }
}
