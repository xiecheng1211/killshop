package com.shopping.miaoshao.vo;

import com.shopping.miaoshao.domain.MiaoshaUser;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class GoodsDetailVo {

    private int miaoshaStatus = 0;
    private long remainSecondes = 0;
    private GoodsVo goods;
    private MiaoshaUser user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public Long getRemainSecondes() {
        return remainSecondes;
    }

    public void setRemainSecondes(Long remainSecondes) {
        this.remainSecondes = remainSecondes;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
