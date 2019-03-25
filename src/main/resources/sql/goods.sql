CREATE TABLE goods(
    id varchar(32) NOT NULL COMMENT '商品ID',
    goods_name varchar(16) DEFAULT NULL COMMENT '商品名称',
    goods_title varchar(64) DEFAULT NULL COMMENT '商品标题',
    goods_img varchar(64) DEFAULT NULL COMMENT '商品图片',
    goods_detail text COMMENT '商品详情介绍',
    goods_price decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
    goods_stock int(11) DEFAULT '0' COMMENT '商品库存， -1 表示没有限制',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 dEFAULT CHARSET=utf8mb4