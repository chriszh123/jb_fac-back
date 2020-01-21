package com.ruoyi.mry.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MryQueryVo implements Serializable{
    private static final long serialVersionUID = -730796019380859588L;

    private Short shopId;
    private Long customerId;
    private Date StartDate;
    private Date endDate;
}
