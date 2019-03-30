/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: GoodsPriceVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * GoodsPriceVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:30
 **/
public class GoodsPriceVo implements Serializable {
    private static final long serialVersionUID = 8669703361743062674L;

    private long goodsId;
    private long id;
    private double originalPrice;
    private double pingtuanPrice;
    private double price;
    private String propertyChildIds;
    private int score;
    private int stores;
    private long userId;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getPingtuanPrice() {
        return pingtuanPrice;
    }

    public void setPingtuanPrice(double pingtuanPrice) {
        this.pingtuanPrice = pingtuanPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPropertyChildIds() {
        return propertyChildIds;
    }

    public void setPropertyChildIds(String propertyChildIds) {
        this.propertyChildIds = propertyChildIds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStores() {
        return stores;
    }

    public void setStores(int stores) {
        this.stores = stores;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
