/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/11
 * Description: 用户曲线图vo
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * 用户曲线图vo
 *
 * @author zhangguifeng
 * @create 2019-01-11 10:24
 **/
public class UserDiagramVo implements Serializable {
    private static final long serialVersionUID = -3715675376121253351L;
    // X日期值：统计日期：年月日
    private String[] xAxisData;
    // 每日用户新增人数
    private String[] seriesUserData;

    public String[] getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String[] xAxisData) {
        this.xAxisData = xAxisData;
    }

    public String[] getSeriesUserData() {
        return seriesUserData;
    }

    public void setSeriesUserData(String[] seriesUserData) {
        this.seriesUserData = seriesUserData;
    }
}
