/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: DiscountReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * DiscountReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:42
 **/
public class DiscountReq implements Serializable {
    private static final long serialVersionUID = 5760053021910910008L;

    private String token;
    private int status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
