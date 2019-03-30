/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: LogisticsDetailVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * LogisticsDetailVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:17
 **/
public class LogisticsDetailVo implements Serializable {
    private static final long serialVersionUID = -2474917459052480487L;

    private double addAmount;
    private int addNumber;
    private double firstAmount;
    private int firstNumber;
    private int type;
    private long userId;

    public double getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(double addAmount) {
        this.addAmount = addAmount;
    }

    public int getAddNumber() {
        return addNumber;
    }

    public void setAddNumber(int addNumber) {
        this.addNumber = addNumber;
    }

    public double getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(double firstAmount) {
        this.firstAmount = firstAmount;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
