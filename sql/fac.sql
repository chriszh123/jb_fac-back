-- --------------------------
-- 1、商品信息表
-- --------------------------
drop table if exists `fac_product`;
CREATE TABLE `fac_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sort` smallint NOT NULL COMMENT '排序',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '售价(抢购价)',
  `sales` smallint NOT NULL DEFAULT '0' COMMENT '销量',
  `status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '上架状态:1-上架,2-下架',
  `category_id` int(10) NOT NULL COMMENT '商品类目',
  `business_id` int(10) NOT NULL COMMENT '所属商家',
  `original_price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '原价',
  `inventory_quantity` smallint NOT NULL DEFAULT '0' COMMENT '库存数量',
  `order_count` smallint NOT NULL DEFAULT '0' COMMENT '订单数量',
  `limit_quantity` smallint NOT NULL DEFAULT '0' COMMENT '每人限购数量',
  `vm_buyer_quantity` smallint NOT NULL DEFAULT '0' COMMENT '虚拟购买人数',
  `distribution` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '分销奖金',
  `bonus_points` smallint NOT NULL DEFAULT '0' COMMENT '奖励积分',
  `rush_start` datetime NULL COMMENT '抢购开始时间',
  `rush_end` datetime NULL COMMENT '抢购结束时间',
  `writeoff_start` datetime NULL COMMENT '核销开始时间',
  `writeoff_end` datetime NULL COMMENT '核销结束时间',
  `picture` varchar(1536) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品图片',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品介绍内容',
  `ship_mode` tinyint(2) NOT NULL DEFAULT '2' COMMENT '发货方式:1-送货上门,2-核销码',
  `ship_cost` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '运费',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品信息表';

-- --------------------------
-- 2、商品类目表
-- --------------------------
drop table if exists `fac_product_category`;
CREATE TABLE `fac_product_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sort` tinyint(2) NOT NULL COMMENT '排序',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类目名称',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  `status` tinyint(2) NOT NULL COMMENT '状态:1-显示；2-隐藏',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品类目表';

-- --------------------------
-- 3、核销记录表
-- --------------------------
drop table if exists `fac_product_writeoff`;
CREATE TABLE `fac_product_writeoff` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号,eg:201812231410342545',
  `prod_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `buyer_id` bigint(20) NOT NULL COMMENT '买者ID',
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '核销码',
  `writeoff_time` datetime COMMENT '核销时间',
  `status` tinyint(2) NOT NULL COMMENT '核销状态:1-已核销,2-待核销',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='核销记录表';

-- --------------------------
-- 4、订单表
-- --------------------------
drop table if exists `fac_order`;
CREATE TABLE `fac_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号,eg:201812231410342545',
  `prod_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `prod_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `prod_number` smallint NOT NULL DEFAULT 0 COMMENT '购买商品数量',
  `price` decimal(8,2) NOT NULL COMMENT '金额',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户token ',
  `open_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户微信openid，唯一 ',
  `user_id` bigint(20) DEFAULT NULL COMMENT '买者用户id',
  `user_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户真实名称',
  `nick_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注',
  `user_score` smallint DEFAULT 0 COMMENT '当前订单使用的积分数，同一订单号下的不同商品这里记录的是重复一样的值',
  `ship_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '快递单号ID',
  `ship_code` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '快递单号',
  `remark_mngt` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '管理员备注',
  `ship` tinyint(2) NOT NULL DEFAULT 2 COMMENT '是否发货:1-已发货;2-未发货',
  `cacel_id` bigint(20) DEFAULT NULL COMMENT '取消订单操作人id',
  `cacel_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消订单操作人名称',
  `cacel_time` datetime DEFAULT NULL COMMENT '取消订单操作时间',
  `prepay_id` bigint(20) DEFAULT NULL COMMENT '微信预支付id',
  `inviter_id` bigint(20) DEFAULT NULL COMMENT '商品分享人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- --------------------------
-- 5、商家表
-- --------------------------
drop table if exists `fac_business`;
CREATE TABLE `fac_business` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商家名称',
  `contacts1` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人1',
  `contacts2` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL COMMENT '联系人2',
  `contacts3` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL COMMENT '联系人3',
  `phone_number1` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '电话1',
  `phone_number2` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL COMMENT '电话2',
  `phone_number3` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL COMMENT '电话3',
  `address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地址',
  `login_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录名',
  `login_pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `secret` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否加密处理',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家表';

-- --------------------------
-- 6、渠道表
-- --------------------------
drop table if exists `fac_channel`;
CREATE TABLE `fac_channel` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地址',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系电话',
  `channel_user_id` bigint(20) DEFAULT NULL COMMENT '绑定用户',
  `status` tinyint(2) NOT NULL COMMENT '状态:1-正常;2-冻结',
  `qrcode` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '当前渠道唯一标志，用于后续分销',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道表';

-- --------------------------
-- 7、买者用户表
-- --------------------------
drop table if exists `fac_buyer`;
CREATE TABLE `fac_buyer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名 ',
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'token ',
  `open_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户微信openid，唯一 ',
  `gender` varchar(2)  COMMENT '性别',
  `avatarUrl` varchar(1024)  COMMENT '微信头像地址',
  `balance` decimal(8,2) NOT NULL COMMENT '余额:分销的奖金',
  `points` smallint NOT NULL COMMENT '积分',
  `registry_time` datetime NOT NULL COMMENT '注册日期,第一次使用本产品时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='买者用户表';

-- --------------------------
-- 8、买者与商家商品关联表
-- --------------------------
drop table if exists `fac_buyer_business`;
CREATE TABLE `fac_buyer_business` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'token ',
  `business_id` bigint(20) DEFAULT NULL COMMENT '绑定商家',
  `business_prod_id` bigint(20) DEFAULT NULL COMMENT '绑定商家的某一个商品',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='买者与商家商品关联表';

-- --------------------------
-- 9、买家提现记录表
-- --------------------------
drop table if exists `fac_cash`;
CREATE TABLE `fac_cash` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `cash` decimal(8,2) NOT NULL COMMENT '提现金额',
  `receiving_account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '收款帐号',
  `phone_number` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '联系电话',
  `apply_time` datetime NOT NULL COMMENT '申请日期',
  `pay_time` datetime  COMMENT '打款时间',
  `status` tinyint(2) NOT NULL COMMENT '状态:1-待处理；2-失败；3-成功',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='买家提现记录表';

-- --------------------------
-- 10、焦点图表
-- --------------------------
drop table if exists `fac_focus_map`;
CREATE TABLE `fac_focus_map` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `sort` tinyint(2) NOT NULL COMMENT '显示顺序',
  `picture` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  `jump_type` tinyint(2) NOT NULL COMMENT '跳转类型:1-页面；2-商品；3-分类',
  `jump_params` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填写跳转类型对应的页面ID、商品ID或分类ID',
  `status` tinyint(2) NOT NULL COMMENT '状态:1-显示；2-隐藏',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='焦点图表';

-- --------------------------
-- 11、菜单表
-- --------------------------
drop table if exists `fac_menu`;
CREATE TABLE `fac_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `sort` tinyint(2) NOT NULL COMMENT '排序',
  `jump_type` tinyint(2) NOT NULL COMMENT '跳转类型:1-页面；2-商品；3-分类',
  `jump_params` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填写跳转类型对应的页面ID、商品ID或分类ID',
  `status` tinyint(2) NOT NULL COMMENT '状态:1-显示；2-隐藏',
  `pic_view` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片预览',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单管理表';


-- --------------------------
-- 12、用户收货地址表
-- --------------------------
drop table if exists `fac_buyer_address`;
CREATE TABLE `fac_buyer_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户token ',
  `open_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户微信openid，唯一 ',
  `address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `provinceId` int(10)  NOT NULL COMMENT '省id ',
  `province_str` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '省名称',
  `cityId` int(10)  NOT NULL COMMENT '市id ',
  `city_str` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '市名称',
  `districtId` int(10)  NOT NULL COMMENT '区id ',
  `district_str` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '区名称',
  `linkMan` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮政编码',
  `is_default` tinyint(1) NOT NULL COMMENT '是否为默认地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='买者用户收货地址表';

-- --------------------------
-- 13、用户签到等积分表
-- --------------------------
drop table if exists `fac_buyer_sign`;
CREATE TABLE `fac_buyer_sign` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户token ',
  `nick_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户昵称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '积分类型：0-签到;1-购物反赠积分,2-购物消费',
  `point` smallint NOT NULL DEFAULT '0' COMMENT '积分数',
  `sign_time` datetime NOT NULL COMMENT '签到时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户签到等积分表';
