/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/11
 * Description: Fac总的统计一览信息
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * Fac总的统计一览信息
 *
 * @author zhangguifeng
 * @create 2019-01-11 14:57
 **/
public class FacStaticVo implements Serializable {
    private static final long serialVersionUID = -6112792965818679247L;

    /**
     * 统计项
     */
    private String[] staticXAxis;
    /**
     * 各统计项数量
     */
    private Integer[] staticData;

    public String[] getStaticXAxis() {
        return staticXAxis;
    }

    public void setStaticXAxis(String[] staticXAxis) {
        this.staticXAxis = staticXAxis;
    }

    public Integer[] getStaticData() {
        return staticData;
    }

    public void setStaticData(Integer[] staticData) {
        this.staticData = staticData;
    }
}
