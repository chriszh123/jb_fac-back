/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/8
 * Description: Fac常量定义类
 */
package com.ruoyi.fac.constant;

/**
 * Fac常量定义类
 *
 * @author zhangguifeng
 * @create 2019-01-08 11:27
 **/
public class FacConstant {
    /**
     * 树节点类型：商家
     */
    public static String NODE_FIELD_TYPE_BIZ = "biz";
    /**
     * 树节点类型：商品
     */
    public static String NODE_FIELD_TYPE_PROD = "prod";

    public static String ERROR_MSG_LOGIN_USER_NULL = "请先登录再继续操作";
    /**
     * ajax请求响应码：请求成功
     */
    public static String AJAX_CODE_SUCCESS = "0";
    /**
     * ajax请求响应码：请求失败
     */
    public static String AJAX_CODE_FAIL = "-1";

    public static String TEST_IMG_URL = "https://cdn.it120.cc/apifactory/2019/01/22/c58792e592ef446360f564bc80ef20a9.jpg";
    /**
     * fac模块涉及到的文件大小:500K
     */
    public static int FILE_SIZE_FAC = 1024 * 500;
    /**
     * 分隔符：+
     */
    public static String SEPARATOR_SEMICOLON = ";";
    /**
     * 商品核销码数字位数
     */
    public static int PRODUCT_WRITEOFF_CODE_LENGTH = 8;
    /**
     * 用户签到领取的随机积分：最大值
     */
    public static  int USER_SIGN_POINT_MAX = 5;
    /**
     * 用户签到领取的随机积分：最小值
     */
    public static  int USER_SIGN_POINT_MIN= 1;

    /**M
     * 伊诺丽尔模块涉及到的文件大小:1
     */
    public static int FILE_SIZE_YNLE = 1024 * 1024;
}
