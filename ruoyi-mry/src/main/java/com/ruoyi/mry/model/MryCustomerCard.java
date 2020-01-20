package com.ruoyi.mry.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MryCustomerCard {
    private Long id;

    private Short shopId;

    private Long customerId;

    private Short cardId;

    private Long shopStaffId;

    private String shopStaffName;

    private String initProIds;

    private BigDecimal price;

    private Byte customeType;

    private Short totalPoints;

    private Short leftPoints;

    private Short totalTimes;

    private Short leftTimes;

    private String proCustomeDesc;

    private String plan;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}