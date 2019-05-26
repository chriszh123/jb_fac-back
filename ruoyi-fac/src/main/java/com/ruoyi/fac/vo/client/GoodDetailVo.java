/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 商品详情数据对象
 */
package com.ruoyi.fac.vo.client;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:15
 **/
@Data
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
    private BusinessVo business;
}
