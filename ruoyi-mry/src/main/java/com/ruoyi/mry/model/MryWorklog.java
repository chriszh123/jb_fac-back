package com.ruoyi.mry.model;

import lombok.Data;

import java.util.Date;

@Data
public class MryWorklog {
    private Long id;

    private Short shopId;

    private Byte type;

    private Byte status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long operatorId;

    private String operatorName;

    private Boolean isDeleted;

    private String content;

    private String contentEdit;
}