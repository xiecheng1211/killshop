package com.shopping.miaoshao.dao;

import com.shopping.miaoshao.domain.MiaoshaOrder;
import com.shopping.miaoshao.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Mapper
public interface OrderDao {

    @Select("select t.* from miaosha_order t where t.user_id = #{userid} and t.goods_id = #{goodsid}")
    public MiaoshaOrder getMiaoshaOrderByUseridAndGoodsID(@Param("userid") String userid, @Param("goodsid") String goodsid);

    @Insert("insert into order_info(id, user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{id}, #{userid}, #{goodsid}, #{goodsname}, #{goodscount}, #{goodsprice}, #{orderchannel},#{status},#{createdate} )")
    public int insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (id, user_id, goods_id, order_id)values(#{id}, #{userid}, #{goodsid}, #{orderid})")
    public int insertmiaoshaorder(MiaoshaOrder miaoshaOrder);

    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from miaosha_order")
    public void deleteMiaoshaOrders();

}
