package com.ruoyi.fac.vo.kanjia;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaVo implements Serializable {
    private static final long serialVersionUID = 4071829778633399194L;
    private Long id;
    private Long prodId;
    private Short total;
    private Short sales;
    private String originalPrice;
    private String price;
    private String min;
    private String max;
    private String startDate;
    private String stopDate;
}
