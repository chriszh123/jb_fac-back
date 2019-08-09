package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaSetVo implements Serializable {
    private Long id;
    // 限量
    private int number = 0;
    // 已售
    private int numberBuy = 0;
    // 原价
    private String originalPrice;
    // 底价
    private String minPrice;
    // 截止
    private String dateEnd;

}
