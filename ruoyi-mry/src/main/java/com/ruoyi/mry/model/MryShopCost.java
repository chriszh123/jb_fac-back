package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MryShopCost {

    @Excel(name = "序号")
    private Long id;

    private Short shopId;

    @Excel(name = "店面名称")
    private String shopName;

    @Excel(name = "消费金额")
    private BigDecimal amount;

    @Excel(name = "消费时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date costTime;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "总金额")
    private String totalAmount = "-";

    private Date createTime;

    private Long operatorId;

    @Excel(name = "操作人")
    private String operatorName;

    @Excel(name = "操作时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private Boolean isDeleted;

    private String costTimeStart;

    private String costTimeEnd;

    private String costTimeStr;
}