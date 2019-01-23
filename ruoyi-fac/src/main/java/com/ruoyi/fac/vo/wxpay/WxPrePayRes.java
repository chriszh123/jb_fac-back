/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/23
 * Description: 微信预支付响应数据结构对象
 */
package com.ruoyi.fac.vo.wxpay;

import java.io.Serializable;

/**
 * 微信预支付响应数据结构对象
 *
 * @author zhangguifeng
 * @create 2019-01-23 9:56
 **/
public class WxPrePayRes implements Serializable {
    private static final long serialVersionUID = 2993957715115899088L;

    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String sign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
