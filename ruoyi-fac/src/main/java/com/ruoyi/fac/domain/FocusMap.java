package com.ruoyi.fac.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.base.BaseEntity;

import java.util.Date;

/**
 * 焦点图表 fac_focus_map
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public class FocusMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 图片
     */
    private String picture;
    /**
     * 跳转类型:1-页面；2-商品；3-分类
     */
    private Integer jumpType;
    /**
     * 填写跳转类型对应的页面ID、商品ID或分类ID
     */
    private String jumpParams;
    /**
     * 状态:1-显示；2-隐藏
     */
    private Integer status;
    /**
     * 图片预览
     */
    private String picView;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpParams(String jumpParams) {
        this.jumpParams = jumpParams;
    }

    public String getJumpParams() {
        return jumpParams;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setPicView(String picView) {
        this.picView = picView;
    }

    public String getPicView() {
        return picView;
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
                .append("title", getTitle())
                .append("sort", getSort())
                .append("picture", getPicture())
                .append("jumpType", getJumpType())
                .append("jumpParams", getJumpParams())
                .append("status", getStatus())
                .append("picView", getPicView())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
