package com.ruoyi.mry.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MryCustomer {

    @Excel(name = "客户ID")
    private Long id;

    @Excel(name = "客户名称")
    private String name;

    private Short shopId;

    @Excel(name = "所属店面")
    private String shopName;

    @Excel(name = "性别", readConverterExp = "0=女,1=男,2=未知")
    private String sex;

    @Excel(name = "家庭住址")
    private String address;

    @Excel(name = "联系电话")
    private String phoneNumber;

    @Excel(name = "生日")
    private String birthday;

    @Excel(name = "职业")
    private String work;

    @Excel(name = "当前拥有总积分")
    private Long totalCustomePoints;

    @Excel(name = "当前剩余积分")
    private Long leftPoints;

    @Excel(name = "当前拥有消费次数")
    private Short totalCustomeTimes;

    @Excel(name = "当前剩余消费次数")
    private Short leftTimes;

    @Excel(name = "备注")
    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;

    /**
     * 客户图片
     */
    private String picture;

    private String imgPath;
}