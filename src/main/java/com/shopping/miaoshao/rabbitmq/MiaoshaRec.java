package com.shopping.miaoshao.rabbitmq;

import com.shopping.miaoshao.domain.MiaoshaOrder;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.service.GoodsService;
import com.shopping.miaoshao.service.MiaoshaService;
import com.shopping.miaoshao.service.OrderService;
import com.shopping.miaoshao.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class MiaoshaRec {

    private static Logger log = LoggerFactory.getLogger(MiaoshaRec.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues="miaosha_queue")
    public void receive(Object object) {
        log.info("receive message:"+object);
        MiaoshaMessage mm  = (MiaoshaMessage)object;
        MiaoshaUser user = mm.getMiaoshaUser();
        String goodsId = mm.getGoodsid();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUseridAndGoodsID(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }
}
