/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderCreateRes
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * OrderCreateRes
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:53
 **/
public class OrderCreateRes implements Serializable {
    private static final long serialVersionUID = -516370769360868722L;

    private double amountLogistics;
    private int score;
    private int goodsNumber;
    private boolean isNeedLogistics = false;
    private double amountTotle;
    private double amount;
    private double amountReal;
    private String dateAdd;
    private String dateClose;
    private boolean hasRefund = false;
    private long id;
    private boolean isCanHx = false;
    private boolean isPay = false;
    private boolean isSuccessPingtuan = false;
    private String orderNumber;
    private String remark;
    private long shopId;
    private int status;
    private String statusStr;
    private int type;

    public double getAmountLogistics() {
        return amountLogistics;
    }

    public void setAmountLogistics(double amountLogistics) {
        this.amountLogistics = amountLogistics;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public boolean isNeedLogistics() {
        return isNeedLogistics;
    }

    public void setNeedLogistics(boolean needLogistics) {
        isNeedLogistics = needLogistics;
    }

    public double getAmountTotle() {
        return amountTotle;
    }

    public void setAmountTotle(double amountTotle) {
        this.amountTotle = amountTotle;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(double amountReal) {
        this.amountReal = amountReal;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateClose() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose = dateClose;
    }

    public boolean isHasRefund() {
        return hasRefund;
    }

    public void setHasRefund(boolean hasRefund) {
        this.hasRefund = hasRefund;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCanHx() {
        return isCanHx;
    }

    public void setCanHx(boolean canHx) {
        isCanHx = canHx;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isSuccessPingtuan() {
        return isSuccessPingtuan;
    }

    public void setSuccessPingtuan(boolean successPingtuan) {
        isSuccessPingtuan = successPingtuan;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
