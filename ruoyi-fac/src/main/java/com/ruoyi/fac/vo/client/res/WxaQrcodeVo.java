package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxaQrcodeVo implements Serializable {
    private static final long serialVersionUID = 2955112452614612474L;

    // 小程序码图片地址
    private String imageUrl;
    // 当前商品价格
    private String prodPrice;
}
