CREATE TABLE miaosha_user (
    id varchar(20) NOT NULL COMMENT '用户ID,手机号码',
    nickname varchar(255) NOT NULL,
    password varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt)',
    salt varchar(10) DEFAULT NULL COMMENT '盐值',
    head varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
    register_date datetime DEFAULT NULL COMMENT '注册时间',
    last_login_date datetime DEFAULT NULL COMMENT '上次登录时间',
    login_count int(11) DEFAULT '0' COMMENT '登录次数',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4