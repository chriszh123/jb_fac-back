package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.util.Date;

/**
 * 商家表 fac_business
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public class Business extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 联系人1
     */
    private String contacts1;
    /**
     * 联系人2
     */
    private String contacts2;
    /**
     * 联系人3
     */
    private String contacts3;
    /**
     * 电话1
     */
    private String phoneNumber1;
    /**
     * 电话2
     */
    private String phoneNumber2;
    /**
     * 电话3
     */
    private String phoneNumber3;
    /**
     * 地址
     */
    private String address;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String loginPwd;
    /**
     * 是否加密处理
     */
    private Integer secret;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContacts1(String contacts1) {
        this.contacts1 = contacts1;
    }

    public String getContacts1() {
        return contacts1;
    }

    public void setContacts2(String contacts2) {
        this.contacts2 = contacts2;
    }

    public String getContacts2() {
        return contacts2;
    }

    public void setContacts3(String contacts3) {
        this.contacts3 = contacts3;
    }

    public String getContacts3() {
        return contacts3;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
    }

    public Integer getSecret() {
        return secret;
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
                .append("name", getName())
                .append("contacts1", getContacts1())
                .append("contacts2", getContacts2())
                .append("contacts3", getContacts3())
                .append("phoneNumber1", getPhoneNumber1())
                .append("phoneNumber2", getPhoneNumber2())
                .append("phoneNumber3", getPhoneNumber3())
                .append("address", getAddress())
                .append("loginName", getLoginName())
                .append("loginPwd", getLoginPwd())
                .append("secret", getSecret())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
