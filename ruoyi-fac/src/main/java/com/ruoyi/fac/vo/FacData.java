/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/23
 * Description: 小程序端请求响应数据对象
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * 小程序端请求响应数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-23 9:49
 **/
public class FacData implements Serializable {
    private static final long serialVersionUID = 4105392764329232955L;

    /**
     * code值为0时，请求正常
     */
    private int code;
    private Object data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
