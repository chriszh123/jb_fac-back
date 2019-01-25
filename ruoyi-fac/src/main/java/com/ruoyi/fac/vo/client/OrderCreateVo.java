/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderCreateVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderCreateVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:45
 **/
public class OrderCreateVo implements Serializable {
    private static final long serialVersionUID = 2538253829809441783L;

    private String token;
    private List<GoodsJsonStrVo> goodsJsonStr = new ArrayList<>();
    private String remark;
    private boolean calculate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<GoodsJsonStrVo> getGoodsJsonStr() {
        return goodsJsonStr;
    }

    public void setGoodsJsonStr(List<GoodsJsonStrVo> goodsJsonStr) {
        this.goodsJsonStr = goodsJsonStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCalculate() {
        return calculate;
    }

    public void setCalculate(boolean calculate) {
        this.calculate = calculate;
    }
}
