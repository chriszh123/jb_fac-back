/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/23
 * Description: 查询微信预支付信息的请求参数
 */
package com.ruoyi.fac.vo.wxpay;

import java.io.Serializable;

/**
 * 查询微信预支付信息的请求参数
 *
 * @author zhangguifeng
 * @create 2019-01-23 9:58
 **/
public class WxPrePayReq implements Serializable {
    private static final long serialVersionUID = 5699573185163152974L;

    private String token;
    private String money;
    private String remark;
    private String payName;
    private NextAction nextAction;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public NextAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(NextAction nextAction) {
        this.nextAction = nextAction;
    }
}
