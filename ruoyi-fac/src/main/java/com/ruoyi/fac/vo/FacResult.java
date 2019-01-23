/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/23
 * Description: 小程序端响应数据接口
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * 小程序端响应数据接口
 *
 * @author zhangguifeng
 * @create 2019-01-23 9:40
 **/
public class FacResult implements Serializable {
    private static final long serialVersionUID = -4878958423798261922L;

    /**
     * statusCode >= 200 && statusCode < 300:请求正常
     */
    private int statusCode;
    private String msg;
    private FacData data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FacData getData() {
        return data;
    }

    public void setData(FacData data) {
        this.data = data;
    }
}
