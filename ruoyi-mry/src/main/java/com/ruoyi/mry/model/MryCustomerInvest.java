package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MryCustomerInvest {
    @Excel(name = "序号")
    private Long id;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    private Long customerId;

    @Excel(name = "客户名称")
    private String customerName;

    @Excel(name = "客户消费卡号")
    private Long cardId;

    @Excel(name = "充值日期", dateFormat = "yyyy-MM-dd")
    private Date investTime;
    private String investTimeStr;

    @Excel(name = "客户充值金额")
    private BigDecimal investPrice;

    @Excel(name = "充值金额对应积分")
    private Long customePoints;

    @Excel(name = "充值金额对应消费次数")
    private Short customeTimes;

    private Date createTime;

    @Excel(name = "操作人")
    private String operatorName;

    @Excel(name = "备注")
    private String remark;

    private Date updateTime;

    private Long operatorId;

    private Boolean isDeleted;
}
