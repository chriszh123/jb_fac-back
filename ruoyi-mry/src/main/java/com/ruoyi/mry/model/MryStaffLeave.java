package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryStaffLeave {
    private Long id;

    private Short shopId;

    private Long staffId;

    @Excel(name = "美容师名称")
    private String staffName;

    @Excel(name = "所在店面")
    private String shopName;

    @Excel(name = "类别", readConverterExp = "1=正常上班,2=事假,3=病假,4=其它")
    private Byte recordType;

    @Excel(name = "开始时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceStart;
    private String serviceStartStr;

    @Excel(name = "结束时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceEnd;
    private String serviceEndStr;

    private Byte needDays;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;
}