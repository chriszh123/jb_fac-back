package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryShop {
    @Excel(name = "店面id")
    private Short id;

    @Excel(name = "店面名称")
    private String name;

    @Excel(name = "店面地址")
    private String address;

    @Excel(name = "状态", readConverterExp = "1=正常营业,2=休业整顿")
    private Byte status;

    @Excel(name = "店面管理人员")
    private String shopMngt;

    @Excel(name = "店面管理人员手机号码")
    private String shopMngtPhone;

    private String contact1;

    private String phoneNumber1;

    private String contact2;

    private String phoneNumber2;

    @Excel(name = "开店日期", dateFormat = "yyyy-MM-dd HH:mm")
    private Date openTime;
    private String openTimeStr;

    @Excel(name = "常营业开始时间", dateFormat = "HH:mm")
    private Date bizTimeStart;
    private String bizTimeStartStr;

    @Excel(name = "常营业结束时间", dateFormat = "HH:mm")
    private Date bizTimeEnd;
    private String bizTimeEndStr;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}