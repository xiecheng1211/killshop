package com.shopping.miaoshao.rabbitmq;

import com.shopping.miaoshao.domain.MiaoshaUser;

import java.io.Serializable;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class MiaoshaMessage implements Serializable {

    private MiaoshaUser miaoshaUser;

    private String goodsid;

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }
}
