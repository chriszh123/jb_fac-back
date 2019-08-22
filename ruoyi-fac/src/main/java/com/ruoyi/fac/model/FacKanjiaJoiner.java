package com.ruoyi.fac.model;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

public class FacKanjiaJoiner {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.kanjia_id
     *
     * @mbggenerated
     */
    @Excel(name = "砍价ID")
    private Long kanjiaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.prod_id
     *
     * @mbggenerated
     */
    @Excel(name = "商品ID")
    private Long prodId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.prod_name
     *
     * @mbggenerated
     */
    @Excel(name = "商品名称")
    private String prodName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.phone_number
     *
     * @mbggenerated
     */
    @Excel(name = "手机号码")
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.nick_name
     *
     * @mbggenerated
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.current_price
     *
     * @mbggenerated
     */
    @Excel(name = "当前价格")
    private BigDecimal currentPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.price
     *
     * @mbggenerated
     */
    @Excel(name = "底价")
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.help_amount
     *
     * @mbggenerated
     */
    @Excel(name = "各助力金额")
    private String helpAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.status
     *
     * @mbggenerated
     */
    @Excel(name = "状态", readConverterExp = "1=进行中,2=无效,3=完成")
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.create_time
     *
     * @mbggenerated
     */
    @Excel(name = "操作时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.operator_id
     *
     * @mbggenerated
     */
    private Long operatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.operator_name
     *
     * @mbggenerated
     */
    private String operatorName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fac_kanjia_joiner.is_deleted
     *
     * @mbggenerated
     */
    private Boolean isDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.id
     *
     * @return the value of fac_kanjia_joiner.id
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.id
     *
     * @param id the value for fac_kanjia_joiner.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.kanjia_id
     *
     * @return the value of fac_kanjia_joiner.kanjia_id
     * @mbggenerated
     */
    public Long getKanjiaId() {
        return kanjiaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.kanjia_id
     *
     * @param kanjiaId the value for fac_kanjia_joiner.kanjia_id
     * @mbggenerated
     */
    public void setKanjiaId(Long kanjiaId) {
        this.kanjiaId = kanjiaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.prod_id
     *
     * @return the value of fac_kanjia_joiner.prod_id
     * @mbggenerated
     */
    public Long getProdId() {
        return prodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.prod_id
     *
     * @param prodId the value for fac_kanjia_joiner.prod_id
     * @mbggenerated
     */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.prod_name
     *
     * @return the value of fac_kanjia_joiner.prod_name
     * @mbggenerated
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.prod_name
     *
     * @param prodName the value for fac_kanjia_joiner.prod_name
     * @mbggenerated
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.phone_number
     *
     * @return the value of fac_kanjia_joiner.phone_number
     * @mbggenerated
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.phone_number
     *
     * @param phoneNumber the value for fac_kanjia_joiner.phone_number
     * @mbggenerated
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.token
     *
     * @return the value of fac_kanjia_joiner.token
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.token
     *
     * @param token the value for fac_kanjia_joiner.token
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.nick_name
     *
     * @return the value of fac_kanjia_joiner.nick_name
     * @mbggenerated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.nick_name
     *
     * @param nickName the value for fac_kanjia_joiner.nick_name
     * @mbggenerated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.current_price
     *
     * @return the value of fac_kanjia_joiner.current_price
     * @mbggenerated
     */
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.current_price
     *
     * @param currentPrice the value for fac_kanjia_joiner.current_price
     * @mbggenerated
     */
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.price
     *
     * @return the value of fac_kanjia_joiner.price
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.price
     *
     * @param price the value for fac_kanjia_joiner.price
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.help_amount
     *
     * @return the value of fac_kanjia_joiner.help_amount
     * @mbggenerated
     */
    public String getHelpAmount() {
        return helpAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.help_amount
     *
     * @param helpAmount the value for fac_kanjia_joiner.help_amount
     * @mbggenerated
     */
    public void setHelpAmount(String helpAmount) {
        this.helpAmount = helpAmount == null ? null : helpAmount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.status
     *
     * @return the value of fac_kanjia_joiner.status
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.status
     *
     * @param status the value for fac_kanjia_joiner.status
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.create_time
     *
     * @return the value of fac_kanjia_joiner.create_time
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.create_time
     *
     * @param createTime the value for fac_kanjia_joiner.create_time
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.update_time
     *
     * @return the value of fac_kanjia_joiner.update_time
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.update_time
     *
     * @param updateTime the value for fac_kanjia_joiner.update_time
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.operator_id
     *
     * @return the value of fac_kanjia_joiner.operator_id
     * @mbggenerated
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.operator_id
     *
     * @param operatorId the value for fac_kanjia_joiner.operator_id
     * @mbggenerated
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.operator_name
     *
     * @return the value of fac_kanjia_joiner.operator_name
     * @mbggenerated
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.operator_name
     *
     * @param operatorName the value for fac_kanjia_joiner.operator_name
     * @mbggenerated
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fac_kanjia_joiner.is_deleted
     *
     * @return the value of fac_kanjia_joiner.is_deleted
     * @mbggenerated
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fac_kanjia_joiner.is_deleted
     *
     * @param isDeleted the value for fac_kanjia_joiner.is_deleted
     * @mbggenerated
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}