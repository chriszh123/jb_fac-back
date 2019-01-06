package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表 fac_order
 *
 * @author ruoyi
 * @date 2019-01-06
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 订单号,eg:201812231410342545
     */
    private String orderNo;
    /**
     * 商品id
     */
    private Long prodId;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 金额
     */
    private BigDecimal price;
    /**
     * 状态:1-已付款;2-待付款；3-已取消;4-未取消
     */
    private Integer status;
    /**
     * 付款时间
     */
    private Date payTime;
    /**
     * 买者用户id
     */
    private Long userId;
    /**
     * 用户真实名称
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 快递单号ID
     */
    private Long shipId;
    /**
     * 快递单号
     */
    private String shipCode;
    /**
     * 管理员备注
     */
    private String remarkMngt;
    /**
     * 是否发货:1-已发货;2-未发货
     */
    private Integer ship;
    /**
     * 取消订单操作人id
     */
    private Long cacelId;
    /**
     * 取消订单操作人名称
     */
    private String cacelName;
    /**
     * 取消订单操作时间
     */
    private Date cacelTime;
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

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setRemarkMngt(String remarkMngt) {
        this.remarkMngt = remarkMngt;
    }

    public String getRemarkMngt() {
        return remarkMngt;
    }

    public void setShip(Integer ship) {
        this.ship = ship;
    }

    public Integer getShip() {
        return ship;
    }

    public void setCacelId(Long cacelId) {
        this.cacelId = cacelId;
    }

    public Long getCacelId() {
        return cacelId;
    }

    public void setCacelName(String cacelName) {
        this.cacelName = cacelName;
    }

    public String getCacelName() {
        return cacelName;
    }

    public void setCacelTime(Date cacelTime) {
        this.cacelTime = cacelTime;
    }

    public Date getCacelTime() {
        return cacelTime;
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
                .append("orderNo", getOrderNo())
                .append("prodId", getProdId())
                .append("prodName", getProdName())
                .append("price", getPrice())
                .append("status", getStatus())
                .append("payTime", getPayTime())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("remark", getRemark())
                .append("shipId", getShipId())
                .append("shipCode", getShipCode())
                .append("remarkMngt", getRemarkMngt())
                .append("ship", getShip())
                .append("cacelId", getCacelId())
                .append("cacelName", getCacelName())
                .append("cacelTime", getCacelTime())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
