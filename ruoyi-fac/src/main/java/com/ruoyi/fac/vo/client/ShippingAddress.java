/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: ShippingAddress
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * ShippingAddress
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:36
 **/
public class ShippingAddress implements Serializable {
    private static final long serialVersionUID = 8186697176895269078L;

    private String token;
    /**
     * "天元西路牛首工业园区"
     */
    private String address;
    /**
     * "江宁区"
     */
    private String areaStr;
    private Long cityId;
    /**
     * "南京市"
     */
    private String cityStr;
    /**
     * 邮政编码
     */
    private String code;
    private String dateAdd;
    private String dateUpdate;
    private Long districtId;
    private Long id;
    /**
     * 是否为默认地址
     */
    private String isDefault = "false";
    /**
     * "张三丰"
     */
    private String linkMan;
    private String mobile;
    private Long provinceId;
    /**
     * "江苏省"
     */
    private String provinceStr;
    private int status = 0;
    private String statusStr = "正常";
    private long uid;
    private long userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
