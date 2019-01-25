/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/order")
public class FacOrderController extends BaseController {

    @PostMapping("/list")
    @ResponseBody
    public FacResult list(String token, int status) {
        OrderListVo list = new OrderListVo();

        return FacResult.success(list);
    }

    @PostMapping("/create")
    @ResponseBody
    public FacResult create(OrderCreateVo order) {
        OrderCreateRes res = new OrderCreateRes();

        return FacResult.success(res);
    }

    @PostMapping("/statistics")
    @ResponseBody
    public FacResult statistics(String token) {
        OrderStatisticsVo statistics = new OrderStatisticsVo();

        return FacResult.success(statistics);
    }

    @PostMapping("/close")
    @ResponseBody
    public FacResult close(String token, long orderId) {

        return FacResult.success("");
    }
}
