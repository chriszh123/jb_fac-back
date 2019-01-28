package com.ruoyi.fac.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.util.Date;

/**
 * 商品类目表 fac_product_category
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public class ProductCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;
    /**
     * 类目名称
     */
    @Excel(name = "类目名称")
    private String name;
    /**
     * 图片
     */
    private String picture;
    private String imgPath;
    /**
     * 状态:1-显示；2-隐藏
     */
    @Excel(name = "状态", readConverterExp = "1=显示,2=隐藏")
    private Integer status;
    /**
     * 操作者ID
     */
    private Long operatorId;
    /**
     * 操作者姓名
     */
    @Excel(name = "创建者")
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

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sort", getSort())
                .append("name", getName())
                .append("picture", getPicture())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
