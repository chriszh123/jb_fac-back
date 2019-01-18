package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/1/6 19:37
 * Description
 */
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 8339390641166290856L;

    // 订单id
    private String id;
    private String orderNo;
    private String userName;
    private String phoneNumber;
    // 收货地址
    private String harvestAddress;
    private String remark;
    private String shipCode;
    private String remarkMngt;
    private String ship;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHarvestAddress() {
        return harvestAddress;
    }

    public void setHarvestAddress(String harvestAddress) {
        this.harvestAddress = harvestAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getRemarkMngt() {
        return remarkMngt;
    }

    public void setRemarkMngt(String remarkMngt) {
        this.remarkMngt = remarkMngt;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }
}
