/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/11
 * Description: 订单曲线图Vo
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * 订单曲线图Vo
 *
 * @author zhangguifeng
 * @create 2019-01-11 13:39
 **/
public class OrderDiagramVo implements Serializable {
    private static final long serialVersionUID = 6148950220057361230L;

    /**
     * X日期值：统计日期：年月日
     */
    private String[] xAxisData;
    /**
     * 订单数量
     */
    private String[] seriesOrderCount;
    /**
     * 订单金额
     */
    private String[] seriesOrderAmount;

    public String[] getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String[] xAxisData) {
        this.xAxisData = xAxisData;
    }

    public String[] getSeriesOrderCount() {
        return seriesOrderCount;
    }

    public void setSeriesOrderCount(String[] seriesOrderCount) {
        this.seriesOrderCount = seriesOrderCount;
    }

    public String[] getSeriesOrderAmount() {
        return seriesOrderAmount;
    }

    public void setSeriesOrderAmount(String[] seriesOrderAmount) {
        this.seriesOrderAmount = seriesOrderAmount;
    }
}
