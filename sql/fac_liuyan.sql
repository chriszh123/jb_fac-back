-- --------------------------
-- 1、用户留言表表
-- --------------------------
drop table if exists `fac_leave_message`;
CREATE TABLE `fac_leave_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '留言用户token ',
  `nick_name` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '留言者昵称',
  `remark` varchar(2048) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户留言',
  `token_answer` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '回复用户留言的人的token ',
  `mngt_remark` varchar(2048) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户留言回复',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否已经回复',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户留言表';

-- --------------------------
-- 2、用户留言回复表表(暂未使用)
-- --------------------------
drop table if exists `fac_leave_message_reply`;
CREATE TABLE `fac_leave_message_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL COMMENT '用户留言ID',
  `token_ask` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '留言用户token ',
  `token_answer` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '回复用户留言的人的token ',
  `mngt_remark` varchar(2048) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户留言回复',

  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `operator_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `operator_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作者姓名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户留言回复表表';
