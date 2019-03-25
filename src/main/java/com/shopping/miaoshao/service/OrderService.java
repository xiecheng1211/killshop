package com.shopping.miaoshao.service;

import com.shopping.miaoshao.dao.OrderDao;
import com.shopping.miaoshao.domain.MiaoshaOrder;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.domain.OrderInfo;
import com.shopping.miaoshao.projectenum.OrderChannelEnum;
import com.shopping.miaoshao.projectenum.OrderstatusEnum;
import com.shopping.miaoshao.util.IdWorker;
import com.shopping.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IdWorker idWorker;

    public MiaoshaOrder getMiaoshaOrderByUseridAndGoodsID(String userid, String goodsid){
        return orderDao.getMiaoshaOrderByUseridAndGoodsID(userid,goodsid);
    }

    public OrderInfo creatOrder(MiaoshaUser miaoshaUser, GoodsVo goodsVo){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(idWorker.nextId()+"");
        orderInfo.setCreatedate(new Date());
        orderInfo.setGoodscount(1);
        orderInfo.setGoodsid(goodsVo.getId());
        orderInfo.setGoodsname(goodsVo.getGoodsname());
        orderInfo.setStatus(OrderstatusEnum.CREATE_NOPAY.getCode());
        orderInfo.setGoodsprice(goodsVo.getMiaoshaPrice());
        orderInfo.setUserid(miaoshaUser.getId());
        orderInfo.setOrderchannel(OrderChannelEnum.PC.getCode());
        orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsid(goodsVo.getId());
        miaoshaOrder.setId(idWorker.nextId()+"");
        miaoshaOrder.setOrderid(orderInfo.getId());
        miaoshaOrder.setUserid(miaoshaUser.getId());
        orderDao.insertmiaoshaorder(miaoshaOrder);
        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }

}
