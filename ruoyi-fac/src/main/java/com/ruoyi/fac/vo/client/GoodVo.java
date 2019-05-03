/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 商品数据对象
 */
package com.ruoyi.fac.vo.client;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:04
 **/
@Data
public class GoodVo implements Serializable {
    private static final long serialVersionUID = 5996920899006528824L;

    private String barCode;
    private long categoryId;
    private String characteristic;
    private double commission;
    private int commissionType;
    private String dateAdd;
    private String dateEnd;
    /**
     * 2019年
     */
    private String dateEndCountDown;
    private String dateStart;
    private String dateUpdate;
    private int gotScore;
    private int gotScoreType;
    private long id;
    private boolean kanjia = false;
    private double kanjiaPrice;
    private int logisticsId;
    private double minPrice;
    private int minScore;
    private String name;
    private int numberFav;
    private int numberGoodReputation;
    private int numberOrders;
    private int numberSells;
    private double originalPrice;
    private int paixu;
    /**
     * https://cdn.it120.cc/apifactory/2019/01/22/b959b8a0f4ed9a3cf3848c6fd6113c5d.gif
     */
    private String pic;
    private boolean pingtuan = false;
    private double pingtuanPrice;
    /**
     * ",10468,"
     */
    private String propertyIds;
    private int recommendStatus;
    private String recommendStatusStr;
    private long shopId;
    private int status;
    private String statusStr;
    private int stores;
    private long userId;
    private String videoId;
    private int views;
    private double weight;
}
