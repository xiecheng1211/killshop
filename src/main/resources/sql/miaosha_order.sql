CREATE TABLE miaosha_order(
    id varchar(32) NOT NULL COMMENT '秒杀订单ID',
    goods_id varchar(32) DEFAULT NULL COMMENT '商品id',
    order_id varchar(32) DEFAULT NULL COMMENT '订单id',
    user_id varchar(32) DEFAULT NULL COMMENT '用户id',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 dEFAULT CHARSET=utf8mb4