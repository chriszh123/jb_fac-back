package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryStaff {
    @Excel(name = "员工ID")
    private Long id;

    private Short shopId;

    @Excel(name = "员工名称")
    private String name;

    @Excel(name = "性别", readConverterExp = "0=女,1=男,2=未知")
    private String sex;

    @Excel(name = "住址")
    private String address;

    @Excel(name = "手机号码")
    private String phoneNumber;

    @Excel(name = "所在店面")
    private String shopName;

    @Excel(name = "生日")
    private String birthday;

    @Excel(name = "状态", readConverterExp = "1=在职,2=离职,3=待入职")
    private Byte status;

    @Excel(name = "入职时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date joinTime;
    private String joinTimeStr;

    @Excel(name = "离职时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date goTime;
    private String goTimeStr;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}