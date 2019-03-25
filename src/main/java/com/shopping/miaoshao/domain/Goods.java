package com.shopping.miaoshao.domain;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class Goods {

    private String id;
    private String goodsname;
    private String goodstitle;
    private String goodsimg;
    private String goodsdetail;
    private double goodsprice;
    private int goodsstock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodstitle() {
        return goodstitle;
    }

    public void setGoodstitle(String goodstitle) {
        this.goodstitle = goodstitle;
    }

    public String getGoodsimg() {
        return goodsimg;
    }

    public void setGoodsimg(String goodsimg) {
        this.goodsimg = goodsimg;
    }

    public String getGoodsdetail() {
        return goodsdetail;
    }

    public void setGoodsdetail(String goodsdetail) {
        this.goodsdetail = goodsdetail;
    }

    public double getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(double goodsprice) {
        this.goodsprice = goodsprice;
    }

    public int getGoodsstock() {
        return goodsstock;
    }

    public void setGoodsstock(int goodsstock) {
        this.goodsstock = goodsstock;
    }
}
