/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: OrderReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * OrderReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:46
 **/
public class OrderReq implements Serializable {
    private static final long serialVersionUID = 4602052785700062519L;

    private String token;
    private int status;
    private String orderIds;

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

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }
}
