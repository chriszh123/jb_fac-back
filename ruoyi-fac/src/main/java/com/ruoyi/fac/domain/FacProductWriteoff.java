package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;
import java.util.Date;

/**
 * 核销记录表 fac_product_writeoff
 * 
 * @author ruoyi
 * @date 2019-04-06
 */
public class FacProductWriteoff extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 订单号,eg:201812231410342545 */
	private String orderNo;
	/** 买者ID */
	private Long buyerId;
	/** 核销码 */
	private String code;
	/** 核销时间 */
	private Date writeoffTime;
	/** 核销状态:1-已核销,2-待核销 */
	private Integer status;
	/** 创建时间 */
	private Date createTime;
	/** 最近更新时间 */
	private Date updateTime;
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
	public void setOrderNo(String orderNo) 
	{
		this.orderNo = orderNo;
	}

	public String getOrderNo() 
	{
		return orderNo;
	}
	public void setBuyerId(Long buyerId) 
	{
		this.buyerId = buyerId;
	}

	public Long getBuyerId() 
	{
		return buyerId;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getCode() 
	{
		return code;
	}
	public void setWriteoffTime(Date writeoffTime) 
	{
		this.writeoffTime = writeoffTime;
	}

	public Date getWriteoffTime() 
	{
		return writeoffTime;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("buyerId", getBuyerId())
            .append("code", getCode())
            .append("writeoffTime", getWriteoffTime())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
