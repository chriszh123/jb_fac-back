/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 商品分类
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * 商品分类
 *
 * @author zhangguifeng
 * @create 2019-01-25 14:45
 **/
public class CategoryVo implements Serializable {
    private static final long serialVersionUID = -8370454213227891501L;

    private String dateAdd;
    /**
     * https://cdn.it120.cc/apifactory/2019/01/22/6f3ce2c59bee0d0d606018da690d3a80.jpg
     */
    private String icon;
    private long id;
    private boolean isUse;
    private String key;
    private long level;
    private String name;
    private int paixu;
    private long pid = 0;
    /**
     * 美食
     */
    private String type;
    private long userId;

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaixu() {
        return paixu;
    }

    public void setPaixu(int paixu) {
        this.paixu = paixu;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
