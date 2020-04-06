package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MryCustomerCard {
    @Excel(name = "序号")
    private Long id;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    private Long customerId;

    @Excel(name = "客户名称")
    private String customerName;

    @Excel(name = "消费卡ID")
    private Short cardId;

    @Excel(name = "卡片消费有效期开始时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceStart;
    private String serviceStartStr;

    @Excel(name = "卡片消费有效期结束时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceEnd;
    private String serviceEndStr;

    @Excel(name = "客户初始化项目")
    private String initProNames;

    private Long shopStaffId;

    private String initProIds;

    @Excel(name = "办卡预充值金额")
    private BigDecimal price;

    @Excel(name = "消费类型", readConverterExp = "0=积分制,1=消费次数")
    private Byte customeType;

    @Excel(name = "总积分")
    private Long totalPoints;

    @Excel(name = "剩余积分")
    private Long leftPoints;

    @Excel(name = "消费总次数")
    private Short totalTimes;

    @Excel(name = "剩余消费次数")
    private Short leftTimes;

    @Excel(name = "办卡美容师")
    private String shopStaffName;

    @Excel(name = "办卡日期", dateFormat = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @Excel(name = "项目消费相关说明")
    private String proCustomeDesc;

    @Excel(name = "方案")
    private String plan;

    @Excel(name = "备注")
    private String remark;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}