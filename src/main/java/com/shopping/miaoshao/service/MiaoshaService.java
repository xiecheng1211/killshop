package com.shopping.miaoshao.service;

import com.shopping.miaoshao.domain.MiaoshaOrder;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.domain.OrderInfo;
import com.shopping.miaoshao.key.MiaoshaKey;
import com.shopping.miaoshao.util.MD5Util;
import com.shopping.miaoshao.util.UUIDUtil;
import com.shopping.miaoshao.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dongcheng
 * create date 2019/3/23
 **/

@Service
@Transactional
public class MiaoshaService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goodsVo){
        goodsService.miaoshaGoods(goodsVo.getId());
        OrderInfo orderInfo = orderService.creatOrder(miaoshaUser,goodsVo);
        return orderInfo;
    }

    public boolean checkPath(MiaoshaUser user, String goodsId, String path) {
        if(user == null || path == null) {
            return false;
        }
        String pathOld = (String) redisTemplate.opsForValue().get(MD5Util.md5(MiaoshaKey.getMiaoshaPath +user.getId() + "_"+ goodsId));
        return path.equals(pathOld);
    }

    public String createMiaoshaPath(MiaoshaUser user, String goodsId) {
        if(user == null || StringUtils.isEmpty(goodsId)) {
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisTemplate.opsForValue().set(MD5Util.md5(MiaoshaKey.getMiaoshaPath + user.getId() + "_"+ goodsId), str);
        return str;
    }

    public String getMiaoshaResult(String userId, String goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUseridAndGoodsID(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderid();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return "-1";
            }else {
                return "0";
            }
        }
    }

    private boolean getGoodsOver(String goodsId) {
        return redisTemplate.hasKey(MiaoshaKey.isGoodsOver.getPrefix()+goodsId);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

}
