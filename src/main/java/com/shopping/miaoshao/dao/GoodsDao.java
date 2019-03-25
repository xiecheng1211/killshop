package com.shopping.miaoshao.dao;

import com.shopping.miaoshao.domain.MiaoshaGoods;
import com.shopping.miaoshao.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.stock_count, mg.start_time, mg.end_time, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.stock_count, mg.start_time, mg.end_time, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id=#{goodsid}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsid")String goodsid);

    @Update("update miaosha_goods t, goods g set t.stock_count=t.stock_count-1, g.goods_stock=g.goods_stock-1 where t.goods_id=g.id and g.id=#{goodsid}")
    public int miaoshaGoods(@Param("goodsid")String goodsid);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    public int resetStock(MiaoshaGoods g);
}
