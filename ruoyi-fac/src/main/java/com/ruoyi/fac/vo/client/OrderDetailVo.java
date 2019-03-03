package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgf
 * Date 2019/3/3 15:50
 * Description
 */
public class OrderDetailVo implements Serializable{
    private static final long serialVersionUID = -8951475488713537615L;

    private OrderInfoVo orderInfo;
    private List<GoodSpecification> goods;

    public OrderInfoVo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoVo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<GoodSpecification> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodSpecification> goods) {
        this.goods = goods;
    }
}
