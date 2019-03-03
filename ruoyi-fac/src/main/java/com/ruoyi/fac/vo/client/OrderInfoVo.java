package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/3/3 15:51
 * Description
 */
public class OrderInfoVo implements Serializable {
    private static final long serialVersionUID = -8951475488713537615L;

    /**
     * 商品金额:原价
     */
    private double amount;
    /**
     * 运费
     */
    private double amountLogistics;
    /**
     * 实际应付总额
     */
    private double amountReal;
    /**
     * 订单状态
     */
    private int status;
    /**
     * 订单状态：描述
     */
    private String statusStr;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountLogistics() {
        return amountLogistics;
    }

    public void setAmountLogistics(double amountLogistics) {
        this.amountLogistics = amountLogistics;
    }

    public double getAmountReal() {
        return amountReal;
    }

    public void setAmountReal(double amountReal) {
        this.amountReal = amountReal;
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
}
