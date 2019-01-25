/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: KeywordVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * KeywordVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:19
 **/
public class KeywordVo implements Serializable {
    private static final long serialVersionUID = -3596686228183931576L;

    private String value;
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
