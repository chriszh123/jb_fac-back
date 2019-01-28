/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/28
 * Description: 商品信息查询条件
 */
package com.ruoyi.fac.vo.condition;

/**
 * 商品信息查询条件
 *
 * @author zhangguifeng
 * @create 2019-01-28 16:10
 **/
public class QueryGoodVo {
    private String categoryId = null;
    private String name = null;
    private Integer page = null;
    private Integer pageSize = null;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
