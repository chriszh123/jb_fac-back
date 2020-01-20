package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryShopCard {

    @Excel(name = "店面消费卡ID")
    private Long id;

    @Excel(name = "店面消费卡名称")
    private String name;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    @Excel(name = "卡片消费有效期开始时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceStart;
    private String serviceStartStr;

    @Excel(name = "卡片消费有效期结束时间", dateFormat = "yyyy-MM-dd HH:mm")
    private Date serviceEnd;
    private String serviceEndStr;

    @Excel(name = "消费次数")
    private Short customeTimes;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;


}