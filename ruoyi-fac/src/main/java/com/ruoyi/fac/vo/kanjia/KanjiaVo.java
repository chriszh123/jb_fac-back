package com.ruoyi.fac.vo.kanjia;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class KanjiaVo implements Serializable {
    private static final long serialVersionUID = 4071829778633399194L;
    private Long id;
    private Long prodId;
    private Short total;
    private Short sales;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private BigDecimal min;
    private BigDecimal max;
    private String startDate;
    private String stopDate;
    private Short helpPeopleCount;
}
