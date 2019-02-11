/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/fac/client/order")
public class FacOrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/listData")
    @ResponseBody
    public FacResult list(String token, int status) {
        OrderListVo list = new OrderListVo();

        return FacResult.success(list);
    }

    @PostMapping("/create")
    @ResponseBody
    public FacResult create(OrderCreateVo order) {
        OrderCreateRes res = this.orderService.createOrderFromClient(order);
        return FacResult.success(res);
    }

    @PostMapping("/statistics")
    @ResponseBody
    public FacResult statistics(String token) {
        OrderStatisticsVo statistics = this.orderService.orderStatistics(token);
        return FacResult.success(statistics);
    }

    @PostMapping("/close")
    @ResponseBody
    public FacResult close(String token, String orderIds) {
        if (StringUtils.isEmpty(token) ||  StringUtils.isEmpty(orderIds)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        this.orderService.closeOrder(token, orderIds);
        return FacResult.success("");
    }
}
