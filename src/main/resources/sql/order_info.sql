CREATE TABLE order_info (
    id varchar(32) NOT NULL COMMENT '订单id',
    user_id varchar(32) DEFAULT NULL COMMENT '用户id',
    goods_id varchar(32) DEFAULT NULL COMMENT '商品id',
    delivery_addr_id varchar(32) DEFAULT NULL COMMENT '收货地址id',
    goods_name varchar(16) DEFAULT NULL COMMENT '商品名称',
    goods_count int(11) DEFAULT '0' COMMENT '商品数量',
    goods_price decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
    order_channel tinyint(4) DEFAULT '0' COMMENT '1pc, 2android, 3ios',
    status tinyint(4) DEFAULT '0' COMMENT '订单状态: 0.新建未支付 1.已支付 2.已发货 3.已收货 4.已退款 5.已完成',
    create_date datetime DEFAULT NULL COMMENT '订单创建时间',
    pay_date datetime DEFAULT NULL COMMENT '订单支付时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=12 dEFAULT CHARSET=utf8mb4