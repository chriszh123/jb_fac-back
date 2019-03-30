/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: ShopReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * ShopReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:51
 **/
public class ShopReq implements Serializable {
    private static final long serialVersionUID = 3312301322663132083L;

    private String categoryId;
    private String nameLike;
    private int page;
    private int pageSize;
    private String id;
    private String goodsId;
    private String propertyChildIds;

    public String getPropertyChildIds() {
        return propertyChildIds;
    }

    public void setPropertyChildIds(String propertyChildIds) {
        this.propertyChildIds = propertyChildIds;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
