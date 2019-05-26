package com.ruoyi.fac.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
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
@Data
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 订单号,eg:2018 12 231410342545
     */
    @Excel(name = "订单号")
    private String orderNo;
    /**
     * 用户token
     */
    @Excel(name = "用户token")
    private String token;
    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;
    /**
     * 商品id
     */
    private Long prodId;
    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String prodName;
    /**
     * 商品数量
     */
    @Excel(name = "商品数量")
    private int prodNumber;
    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal price;
    /**
     * 当前订单用户使用的积分
     */
    @Excel(name = "用户使用积分数")
    private short userScore;
    /**
     * 状态:1-已付款;2-待付款；3-已取消;4-未取消
     */
    @Excel(name = "状态", readConverterExp = "0=待付款,1=待核销,2=待评价,3=已完成,4=已取消,5=商家待核销")
    private Integer status;
    /**
     * 付款时间
     */
    @Excel(name = "付款时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    /**
     * 商品分享人
     */
    @Excel(name = "商品分享人")
    private Long inviterId;
    /**
     * 用户微信openid，唯一
     */
    private String openId;
    /**
     * 买者用户id
     */
    private Long userId;
    /**
     * 用户真实名称
     */
    private String userName;
    /**
     * 快递单号ID
     */
    private Long shipId;
    /**
     * 快递单号
     */
    private String shipCode;
    /**
     * 用户订单备注
     */
    @Excel(name = "用户订单备注")
    private String remark;
    /**
     * 管理员备注
     */
    @Excel(name = "管理员备注")
    private String remarkMngt;
    /**
     * 是否发货:1-已发货;2-未发货
     */
    @Excel(name = "是否发货", readConverterExp = "1=已发货,2=未发货")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cacelTime;
    /**
     * 微信预支付id
     */
    private Long prepayId;
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
