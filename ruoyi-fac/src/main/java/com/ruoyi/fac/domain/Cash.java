package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 买家提现记录表 fac_cash
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public class Cash extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 用户id */
	private Long userId;
	/** 提现金额 */
	private BigDecimal cash;
	/** 收款帐号 */
	private String receivingAccount;
	/** 联系电话 */
	private String phoneNumber;
	/** 申请日期 */
	private Date applyTime;
	/** 打款时间 */
	private Date payTime;
	/** 状态:1-待处理；2-失败；3-成功 */
	private Integer status;
	/** 操作者ID */
	private Long operatorId;
	/** 操作者姓名 */
	private String operatorName;
	/** 是否删除 */
	private Integer isDeleted;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}

	public Long getUserId() 
	{
		return userId;
	}
	public void setCash(BigDecimal cash) 
	{
		this.cash = cash;
	}

	public BigDecimal getCash() 
	{
		return cash;
	}
	public void setReceivingAccount(String receivingAccount) 
	{
		this.receivingAccount = receivingAccount;
	}

	public String getReceivingAccount() 
	{
		return receivingAccount;
	}
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	public void setApplyTime(Date applyTime) 
	{
		this.applyTime = applyTime;
	}

	public Date getApplyTime() 
	{
		return applyTime;
	}
	public void setPayTime(Date payTime) 
	{
		this.payTime = payTime;
	}

	public Date getPayTime() 
	{
		return payTime;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setOperatorId(Long operatorId)
	{
		this.operatorId = operatorId;
	}

	public Long getOperatorId() 
	{
		return operatorId;
	}
	public void setOperatorName(String operatorName) 
	{
		this.operatorName = operatorName;
	}

	public String getOperatorName() 
	{
		return operatorName;
	}
	public void setIsDeleted(Integer isDeleted) 
	{
		this.isDeleted = isDeleted;
	}

	public Integer getIsDeleted() 
	{
		return isDeleted;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("cash", getCash())
            .append("receivingAccount", getReceivingAccount())
            .append("phoneNumber", getPhoneNumber())
            .append("applyTime", getApplyTime())
            .append("payTime", getPayTime())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
