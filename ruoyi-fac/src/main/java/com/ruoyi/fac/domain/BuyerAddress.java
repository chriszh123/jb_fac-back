package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.util.Date;

/**
 * 买者用户收货地址表 fac_buyer_address
 *
 * @author ruoyi
 * @date 2019-01-28
 */
public class BuyerAddress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 用户id
     */
    private Long buyerId;
    /**
     * 用户token
     */
    private String token;
    /**
     * 用户昵称
     */
    private String address;
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 省名称
     */
    private String provinceStr;
    /**
     * 市id
     */
    private Integer cityId;
    /**
     * 市名称
     */
    private String cityStr;
    /**
     * 区id
     */
    private Integer districtId;
    /**
     * 区名称
     */
    private String districtStr;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 邮政编码
     */
    private String code;
    /**
     * 是否为默认地址
     */
    private Integer isDefault;
    /**
     * 操作者ID
     */
    private Long operatorId;
    /**
     * 操作者姓名
     */
    private String operatorName;
    /**
     * 是否删除
     */
    private Integer isDeleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictStr(String districtStr) {
        this.districtStr = districtStr;
    }

    public String getDistrictStr() {
        return districtStr;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("buyerId", getBuyerId())
                .append("token", getToken())
                .append("address", getAddress())
                .append("provinceId", getProvinceId())
                .append("provinceStr", getProvinceStr())
                .append("cityId", getCityId())
                .append("cityStr", getCityStr())
                .append("districtId", getDistrictId())
                .append("districtStr", getDistrictStr())
                .append("linkMan", getLinkMan())
                .append("phoneNumber", getPhoneNumber())
                .append("code", getCode())
                .append("isDefault", getIsDefault())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
