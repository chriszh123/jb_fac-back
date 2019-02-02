/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: GoodSpecification
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * GoodSpecification
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:40
 **/
public class GoodSpecification implements Serializable {
    private static final long serialVersionUID = 1666661368224208351L;

    private double amount = 0.00;
    private long goodsId;
    private String goodsName;
    private long id;
    private int number;
    private long orderId;
    /**
     * "https://cdn.it120.cc/apifactory/2019/01/22/b959b8a0f4ed9a3cf3848c6fd6113c5d.gif"
     */
    private String pic;
    private String property = "";
    private int score = 0;
    private long uid;
    private long userId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
