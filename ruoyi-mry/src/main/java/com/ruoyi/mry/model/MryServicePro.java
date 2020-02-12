package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryServicePro {

    @Excel(name = "服务项目ID")
    private Short id;

    @Excel(name = "服务项目名称")
    private String name;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    @Excel(name = "服务周期")
    private String serviceCircle;

    @Excel(name = "项目功效描述")
    private String proDesc;

    private String staffId;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}