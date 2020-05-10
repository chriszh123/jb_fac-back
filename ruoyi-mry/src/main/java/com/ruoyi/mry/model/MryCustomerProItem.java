package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MryCustomerProItem {
    @Excel(name = "序号")
    private Long id;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    private Long customerId;

    @Excel(name = "客户名称")
    private String customerName;

    @Excel(name = "消费卡ID")
    private Long cardId;

    private Short proId;

    @Excel(name = "消费项目")
    private String proName;

    @Excel(name = "消费日期", dateFormat = "yyyy-MM-dd")
    private Date consumeTime;
    private String consumeTimeStr;

    @Excel(name = "消费类型", readConverterExp = "0=积分制,1=消费次数")
    private Byte customeType;

    @Excel(name = "当前服务当次开始时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceStart;
    private String serviceStartStr;

    @Excel(name = "当前服务当次结束时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceEnd;
    private String serviceEndStr;

    private Long staff1Id;

    private Long staff2Id;

    private Long staff3Id;

    @Excel(name = "当前服务当次消费金额")
    private BigDecimal customPrice;

    @Excel(name = "当前服务消费积分")
    private Short customePoints;

    @Excel(name = "客户反馈意见")
    private String customerRemark;

    @Excel(name = "美容师反馈意见")
    private String staffRemark;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}