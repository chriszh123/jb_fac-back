/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: GoodsJsonStrVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * GoodsJsonStrVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:46
 **/
public class GoodsJsonStrVo implements Serializable {
    private static final long serialVersionUID = 173287413952467385L;

    private long goodsId;
    private int number;
    private String propertyChildIds;
    private int logisticsType;
    private long inviter_id;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPropertyChildIds() {
        return propertyChildIds;
    }

    public void setPropertyChildIds(String propertyChildIds) {
        this.propertyChildIds = propertyChildIds;
    }

    public int getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(int logisticsType) {
        this.logisticsType = logisticsType;
    }

    public long getInviter_id() {
        return inviter_id;
    }

    public void setInviter_id(long inviter_id) {
        this.inviter_id = inviter_id;
    }
}
