package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表 fac_product
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public class Product extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 售价(抢购价)
     */
    private BigDecimal price;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * 上架状态
     */
    private Integer status;
    /**
     * 商品类目
     */
    private Integer categoryId;
    /**
     * 所属商家
     */
    private Integer businessId;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 库存数量
     */
    private Integer inventoryQuantity;
    /**
     * 每人限购数量
     */
    private Integer limitQuantity;
    /**
     * 虚拟购买人数
     */
    private Integer vmBuyerQuantity;
    /**
     * 分销奖金
     */
    private BigDecimal distribution;
    /**
     * 奖励积分
     */
    private Integer bonusPoints;
    /**
     * 抢购开始时间
     */
    private Date rushStart;
    /**
     * 抢购结束时间
     */
    private Date rushEnd;
    /**
     * 核销开始时间
     */
    private Date writeoffStart;
    /**
     * 核销结束时间
     */
    private Date writeoffEnd;
    /**
     * 商品图片
     */
    private String picture;
    /**
     * 商品介绍内容url
     */
    private String introduction;
    /**
     * 发货方式
     */
    private Integer shipMode;
    /**
     * 运费
     */
    private BigDecimal shipCost;
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

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getSales() {
        return sales;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setLimitQuantity(Integer limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public Integer getLimitQuantity() {
        return limitQuantity;
    }

    public void setVmBuyerQuantity(Integer vmBuyerQuantity) {
        this.vmBuyerQuantity = vmBuyerQuantity;
    }

    public Integer getVmBuyerQuantity() {
        return vmBuyerQuantity;
    }

    public void setDistribution(BigDecimal distribution) {
        this.distribution = distribution;
    }

    public BigDecimal getDistribution() {
        return distribution;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setRushStart(Date rushStart) {
        this.rushStart = rushStart;
    }

    public Date getRushStart() {
        return rushStart;
    }

    public void setRushEnd(Date rushEnd) {
        this.rushEnd = rushEnd;
    }

    public Date getRushEnd() {
        return rushEnd;
    }

    public void setWriteoffStart(Date writeoffStart) {
        this.writeoffStart = writeoffStart;
    }

    public Date getWriteoffStart() {
        return writeoffStart;
    }

    public void setWriteoffEnd(Date writeoffEnd) {
        this.writeoffEnd = writeoffEnd;
    }

    public Date getWriteoffEnd() {
        return writeoffEnd;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setShipMode(Integer shipMode) {
        this.shipMode = shipMode;
    }

    public Integer getShipMode() {
        return shipMode;
    }

    public void setShipCost(BigDecimal shipCost) {
        this.shipCost = shipCost;
    }

    public BigDecimal getShipCost() {
        return shipCost;
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
                .append("sort", getSort())
                .append("name", getName())
                .append("price", getPrice())
                .append("sales", getSales())
                .append("status", getStatus())
                .append("categoryId", getCategoryId())
                .append("businessId", getBusinessId())
                .append("originalPrice", getOriginalPrice())
                .append("inventoryQuantity", getInventoryQuantity())
                .append("limitQuantity", getLimitQuantity())
                .append("vmBuyerQuantity", getVmBuyerQuantity())
                .append("distribution", getDistribution())
                .append("bonusPoints", getBonusPoints())
                .append("rushStart", getRushStart())
                .append("rushEnd", getRushEnd())
                .append("writeoffStart", getWriteoffStart())
                .append("writeoffEnd", getWriteoffEnd())
                .append("picture", getPicture())
                .append("introduction", getIntroduction())
                .append("shipMode", getShipMode())
                .append("shipCost", getShipCost())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
