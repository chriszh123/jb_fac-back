package com.ruoyi.fac.model;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class FacLeaveMessage {
    @Excel(name = "留言ID")
    private Long id;

    private String token;

    @Excel(name = "留言用户")
    private String nickName;

    @Excel(name = "留言时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Excel(name = "留言内容")
    private String remark;

    private String tokenAnswer;

    @Excel(name = "留言回复")
    private String mngtRemark;

    private Boolean status;

    @Excel(name = "回复时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Excel(name = "操作人")
    private String operatorName;

    private Long operatorId;

    @Excel(name = "数据状态", readConverterExp ="0=正常,1=删除")
    private Boolean isDeleted;
}