package com.ruoyi.mry.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDiagramVo implements Serializable {
    private static final long serialVersionUID = 4581518922983171669L;

    /**
     * X日期值：统计日期：年月日
     */
    private String[] xAxisData;
    /**
     * 用户相关记录：消费
     */
    private String[] seriesCustomerData;
}
