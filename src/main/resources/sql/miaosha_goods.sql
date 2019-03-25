CREATE TABLE miaosha_goods(
    id varchar(32) NOT NULL COMMENT '秒杀商品ID',
    goods_id varchar(32) DEFAULT NULL COMMENT '商品id',
    miaosha_price decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
    stock_count int(11) DEFAULT '0' COMMENT '库存数量',
    start_time datetime DEFAULT NULL COMMENT '秒杀开始时间',
    end_time datetime DEFAULT NULL COMMENT '秒杀结束时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 dEFAULT CHARSET=utf8mb4