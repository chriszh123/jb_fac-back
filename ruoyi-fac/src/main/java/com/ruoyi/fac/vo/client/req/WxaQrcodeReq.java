package com.ruoyi.fac.vo.client.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxaQrcodeReq implements Serializable {
    private static final long serialVersionUID = 5895285433287634499L;

    /**
     * 商品id
     */
    private String productId;
    /**
     * 邀请人uid
     */
    private Long inviterUid = null;
    /**
     * 商品详情页路由页面地址：pages/goods-details/index
     */
    private String page;

    private Boolean is_hyaline;

    private Integer expireHours;
}
