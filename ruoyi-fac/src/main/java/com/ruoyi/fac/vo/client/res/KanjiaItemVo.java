package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaItemVo implements Serializable{
    private static final long serialVersionUID = 7757996177877861046L;

    private Long kanjiaId;
    private Long goodsId;
    private String originalPrice;
    private String minPrice;
}
