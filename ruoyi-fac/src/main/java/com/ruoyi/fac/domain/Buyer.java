package com.ruoyi.fac.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 买者用户表 fac_buyer
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public class Buyer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    @Excel(name = "用户ID")
    private Long id;
    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;
    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String name;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phoneNumber;
    /**
     * 余额:分销的奖金
     */
    @Excel(name = "余额")
    private BigDecimal balance;
    /**
     * 积分
     */
    @Excel(name = "积分")
    private Integer points;
    /**
     * 注册日期,第一次使用本产品时间
     */
    @Excel(name = "注册日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registryTime;
    /**
     * 收获地址
     */
    @Excel(name = "收获地址")
    private String harvestAddress;
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

    /**
     * 当前用户绑定的商家商品
     */
    private String[] prodIds;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public void setRegistryTime(Date registryTime) {
        this.registryTime = registryTime;
    }

    public Date getRegistryTime() {
        return registryTime;
    }

    public void setHarvestAddress(String harvestAddress) {
        this.harvestAddress = harvestAddress;
    }

    public String getHarvestAddress() {
        return harvestAddress;
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

    public String[] getProdIds() {
        return prodIds;
    }

    public void setProdIds(String[] prodIds) {
        this.prodIds = prodIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("nickName", getNickName())
                .append("name", getName())
                .append("phoneNumber", getPhoneNumber())
                .append("balance", getBalance())
                .append("points", getPoints())
                .append("registryTime", getRegistryTime())
                .append("harvestAddress", getHarvestAddress())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
