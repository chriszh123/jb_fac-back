/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderCreateVo
 */
package com.ruoyi.fac.vo.client;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderCreateVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:45
 **/
@Data
public class OrderCreateVo implements Serializable {
    private static final long serialVersionUID = 2538253829809441783L;

    private String token;
    private String goodsJsonStr;
    private List<GoodsJsonStrVo> goodsJson = new ArrayList<>();
    private String remark;
    private boolean calculate;
    /**
     * 是否使用积分:0-不使用，1-使用
     */
    private int userScore = 0;
}
