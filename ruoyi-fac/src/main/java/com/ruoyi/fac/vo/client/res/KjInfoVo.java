package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class KjInfoVo implements Serializable {
    private static final long serialVersionUID = 6259264998822685105L;

    private Long prodId;
    private BigDecimal curPrice;
    private int helpNumber = 0;
    private String statusStr;
    private String dateAdd;
    private Long uid;
    // 是否已经达到砍价的底价
    private Boolean upToMinPrice;
}
