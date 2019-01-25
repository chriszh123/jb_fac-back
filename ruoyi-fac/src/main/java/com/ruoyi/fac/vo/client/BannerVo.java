/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 首页轮播图片数据对象
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * 首页轮播图片数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-25 14:35
 **/
public class BannerVo implements Serializable {
    private static final long serialVersionUID = 1034218563706582360L;

    private long businessId;
    /**
     * 2019-01-22 19:10:02
     */
    private String dateAdd;
    /**
     * 2019-01-22 22:17:24
     */
    private String dateUpdate;
    private long id;
    private String linkUrl;
    private int paixu = 0;
    /**
     * "https://cdn.it120.cc/apifactory/2019/01/22/c58792e592ef446360f564bc80ef20a9.jpg"
     */
    private String picUrl;
    private String remark;
    private int status = 0;
    private String statusStr;
    private String title;
    private String type;
    private long userId = 0;

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getPaixu() {
        return paixu;
    }

    public void setPaixu(int paixu) {
        this.paixu = paixu;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
