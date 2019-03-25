package com.shopping.miaoshao.vo;

import com.shopping.miaoshao.domain.Goods;

import java.util.Date;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class GoodsVo extends Goods {

    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startTime;
    private Date endTime;

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
