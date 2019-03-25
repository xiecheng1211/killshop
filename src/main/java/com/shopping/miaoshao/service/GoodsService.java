package com.shopping.miaoshao.service;

import com.shopping.miaoshao.dao.GoodsDao;
import com.shopping.miaoshao.domain.MiaoshaGoods;
import com.shopping.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Service
@Transactional
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> goodsVoList(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(String goodsid){
        return goodsDao.getGoodsVoByGoodsId(goodsid);
    }

    public void miaoshaGoods(String goodsid){
        goodsDao.miaoshaGoods(goodsid);
    }

    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }

}
