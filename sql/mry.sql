-- --------------------------
-- 1、店面
-- --------------------------
drop table if exists `mry_basic_shop`;
CREATE TABLE `mry_basic_shop` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '店面名称',
  `address` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '店面地址',
  `status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '营业状态：1-正常营业,2-休业整顿',
  `shop_mngt` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '店面管理人员',
  `shop_mngt_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '店面管理人员的手机号码',
  `contact1` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '联系人1',
  `phone_number1` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '联系人1的手机号码',
  `contact2` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '联系人2',
  `phone_number2` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '联系人2的手机号码',
  `biz_time_start` datetime NOT NULL COMMENT '日常营业开始时间',
  `biz_time_end` datetime NOT NULL COMMENT '日常营业结束时间',
  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注说明',
  `open_time` datetime NOT NULL COMMENT '开店日期',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店面信息表';

-- --------------------------
-- 2、店面服务项目
-- --------------------------
drop table if exists `mry_basic_service_pro`;
CREATE TABLE `mry_basic_service_pro` (
  `id` smallint NOT NULL AUTO_INCREMENT,
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '服务项目名称 ',
  `shop_id` smallint NOT NULL COMMENT '当前服务项目所属店面',
  `pro_desc` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '服务项目简单功效描述',
  `service_circle` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '服务项目周期 ',
  `staff_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '当前服务项目可操作员工id，多个逗号分隔',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '项目备注信息',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店面服务项目';

-- --------------------------
-- 3、店面客户资料管理
-- --------------------------
drop table if exists `mry_customer`;
CREATE TABLE `mry_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '名称 ',
  `sex` varchar(1) DEFAULT '0' COMMENT '性别：0-女,1-男,2-未知',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '生日：yyyy-mm-dd',
  `address` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '家庭住址',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '联系电话',
  `work` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '职业',
  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注说明',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店面客户资料管理';

-- --------------------------
-- 4、日志管理
-- --------------------------
drop table if exists `mry_worklog`;
CREATE TABLE `mry_worklog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '日志内容',
  `type` tinyint(2) DEFAULT 1 COMMENT '类型：1-日常工作日志，2-需要完成的事情',
  `status` tinyint(2) DEFAULT 1 COMMENT '类型：1-待完成，2-已完成，3-日常工作日志',
  `remark` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注说明',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日志管理';

-- --------------------------
-- 5、员工管理
-- --------------------------
drop table if exists `mry_staff`;
CREATE TABLE `mry_staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '名称 ',
  `sex` varchar(1) DEFAULT '0' COMMENT '性别：0-女,1-男,2-未知',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '生日：yyyy-mm-dd',
  `address` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '地址',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  default '' COMMENT '手机号码',
  `status` tinyint(2) DEFAULT 1 COMMENT '状态：1-在职，2-离职，3-待入职',
  `join_time` datetime COMMENT '入职时间',
  `go_time` datetime  COMMENT '离职时间',
  `remark` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注说明',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工管理';

-- --------------------------
-- 6、客户服务项目消费管理明细：每来店里服务一次就记录一次
-- --------------------------
drop table if exists `mry_customer_pro_item`;
CREATE TABLE `mry_customer_pro_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `card_id` bigint(20) NOT NULL COMMENT '当前服务对应的卡ID',
  `pro_id` smallint NOT NULL COMMENT '当前服务项目ID',
  `custome_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '消费类型：0-积分制，1-消费次数',

  `service_start` datetime NOT NULL COMMENT '当前服务当次开始时间',
  `service_end` datetime NOT NULL COMMENT '当前服务当次结束时间',

  `staff1_id` bigint(20) NOT NULL COMMENT '当前消费项目服务员工1',
  `staff2_id` bigint(20) default 0 COMMENT '当前消费项目服务员工2',
  `staff3_id` bigint(20) default 0 COMMENT '当前消费项目服务员工3',

  `custom_price` decimal(8,2) DEFAULT '0.0' COMMENT '当前服务当次消费金额',

  `custome_points` smallint default 0 COMMENT '当前服务消费积分',

  `customer_remark` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '客户反馈意见',
  `staff_remark` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '员工反馈意见',

  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注',

  `create_time` datetime NOT NULL COMMENT '消费日期',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户服务项目消费管理明细';

-- --------------------------
-- 8、店面消费卡管理
-- --------------------------
drop table if exists `mry_basic_shop_card`;
CREATE TABLE `mry_basic_shop_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '名称 ',
  `service_start` datetime COMMENT '卡片消费开始时间',
  `service_end` datetime COMMENT '卡片消费结束时间',

  `custome_times` smallint default 0 COMMENT '消费次数',

  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店面消费卡管理';

-- --------------------------
-- 9、客户消费卡管理
-- --------------------------
drop table if exists `mry_customer_card`;
CREATE TABLE `mry_customer_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `card_id` smallint NOT NULL COMMENT '消费卡ID',
  `shop_staff_id` bigint(20) DEFAULT NULL COMMENT '店面办卡员工id',
  `shop_staff_Name` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '店面办卡员工名称',
  `init_pro_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '客户初始化项目',
  `price` decimal(8,2) DEFAULT '0.0' COMMENT '客户初始办卡充值金额',
  `custome_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '消费类型：0-积分制，1-消费次数',

  `total_points` bigint(20) default 0 COMMENT '总积分',
  `left_points` bigint(20) default 0 COMMENT '剩余积分',

  `total_times` smallint default 0 COMMENT '消费总次数',
  `left_times` smallint default 0 COMMENT '剩余消费次数',

  `pro_custome_desc` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '项目消费次数相关说明',

  `plan` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '方案',

  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '备注',

  `create_time` datetime NOT NULL COMMENT '办卡日期',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户消费卡管理';

-- --------------------------
-- 10、员工休假管理
-- --------------------------
drop table if exists `mry_staff_leave`;
CREATE TABLE `mry_staff_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` smallint NOT NULL COMMENT '所属店面',
  `staff_id` bigint(20) NOT NULL COMMENT '员工ID',
  `service_start` datetime COMMENT '卡片消费开始时间',
  `service_end` datetime COMMENT '卡片消费结束时间',
  `reason` varchar(1512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci default '' COMMENT '请假原因',
  `need_days` tinyint(2) default 0 COMMENT '当前月天数',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工休假管理';

