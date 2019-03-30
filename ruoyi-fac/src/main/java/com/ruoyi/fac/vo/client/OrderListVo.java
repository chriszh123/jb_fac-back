/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderListVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * OrderListVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:45
 **/
public class OrderListVo implements Serializable {
    private static final long serialVersionUID = -3846053193093789388L;

    private List<OrderVo> orderList = new ArrayList<>();
    /**
     * <商品id，[各种规格对应的订单]>
     */
    private Map<String, List<GoodSpecification>> goodsMap = new HashMap();

    public List<OrderVo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderVo> orderList) {
        this.orderList = orderList;
    }

    public Map<String, List<GoodSpecification>> getGoodsMap() {
        return goodsMap;
    }

    public void setGoodsMap(Map<String, List<GoodSpecification>> goodsMap) {
        this.goodsMap = goodsMap;
    }
}
