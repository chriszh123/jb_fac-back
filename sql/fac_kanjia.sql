-- --------------------------
-- 1、商品砍价设置表
-- --------------------------
drop table if exists `fac_kanjia`;
CREATE TABLE `fac_kanjia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prod_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品id',
  `prod_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `total` smallint NOT NULL DEFAULT '0' COMMENT '总份数',
  `sales` smallint NOT NULL DEFAULT '0' COMMENT '销量',
  `original_price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '原价',
  `price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '底价',
  `original_price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '原价',
  `min` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '砍价一次随机减少的金额范围:最小值',
  `max` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '砍价一次随机减少的金额范围:最大值',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态:1-正常,2-禁用',
  `start_date` datetime NULL COMMENT '开始时间',
  `stop_date` datetime NULL COMMENT '截止时间',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品砍价设置表';

-- --------------------------
-- 2、参与商品砍价活动人员记录表
-- --------------------------
drop table if exists `fac_kanjia_joiner`;
CREATE TABLE `fac_kanjia_joiner` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `kanjia_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '砍价id',
  `prod_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品id',
  `prod_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `phone_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与用户token ',
  `nick_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `current_price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '当前价格',
  `price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '底价',
  `help_people_count` smallint NOT NULL DEFAULT '0' COMMENT '帮砍人数,包括自己',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态:1-进行中;2-砍价完成;3-砍价未完成(已过期)',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参与商品砍价活动人员记录表';

-- --------------------------
-- 3、帮砍价人员明细表
-- --------------------------
drop table if exists `fac_kanjia_helper_item`;
CREATE TABLE `fac_product_writeoff` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `kanjia_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '砍价id',
  `join_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '参与商品砍价活动记录id',
  `prod_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品id',
  `prod_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `token_joiner` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与砍价用户token ',
  `nick_name_joiner` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与砍价用户昵称',
  `phone_number_joiner` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参与砍价用户手机号码',
  `token_helper` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '助力砍价用户token ',
  `nick_name_helper` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '助力砍价用户昵称',
  `phone_number_helper` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '助力砍价用户手机号码',
  `help_price` decimal(8,2) NOT NULL DEFAULT '0.0' COMMENT '助力砍价用户对应的助力金额',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帮砍价人员明细表';

