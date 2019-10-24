package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class KjHelperVo implements Serializable {
    private static final long serialVersionUID = -4860049027141146115L;

    private String token;
    private String avatarUrl;
    // 当前助力人员协助的砍价金额
    private BigDecimal cutPrice;
    // 当前助力人员昵称
    private String nick;
    // 当前助力人员协助的砍价时间
    private String dateAdd;
}
