/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 商品详情数据对象
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:15
 **/
public class GoodDetailVo implements Serializable {
    private static final long serialVersionUID = -2708562455869612067L;

    /**
     * 物流
     */
    private LogisticsVo logistics;
    private CategoryVo category;
    private List<PicsVo> pics = new ArrayList<>();
    private String content;
    private GoodVo basicInfo;

    public LogisticsVo getLogistics() {
        return logistics;
    }

    public void setLogistics(LogisticsVo logistics) {
        this.logistics = logistics;
    }

    public CategoryVo getCategory() {
        return category;
    }

    public void setCategory(CategoryVo category) {
        this.category = category;
    }

    public List<PicsVo> getPics() {
        return pics;
    }

    public void setPics(List<PicsVo> pics) {
        this.pics = pics;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GoodVo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(GoodVo basicInfo) {
        this.basicInfo = basicInfo;
    }
}
