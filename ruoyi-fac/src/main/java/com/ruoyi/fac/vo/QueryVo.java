package com.ruoyi.fac.vo;

import java.util.Date;

/**
 * Created by zgf
 * Date 2019/1/12 21:44
 * Description
 */
public class QueryVo {
    private Date startDate;
    private Date endDate;
    private String token;
    private String openId;
    private Integer status;
    private Long[] orderIds;

    public QueryVo() {
    }

    public QueryVo(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long[] getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(Long[] orderIds) {
        this.orderIds = orderIds;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
