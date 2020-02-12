package com.ruoyi.mry.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MryQueryVo implements Serializable{
    private static final long serialVersionUID = -730796019380859588L;

    private Short shopId = null;
    private Long customerId = null;
    private Date startDate = null;
    private Date endDate = null;
    private String name = null;
}
