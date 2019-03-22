/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.client.req.OrderReq;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public FacResult list(@RequestBody OrderReq req) {
        OrderListVo list = this.orderService.orderList(req.getToken(), req.getStatus());
        return FacResult.success(list);
    }

    @PostMapping("/create")
    @ResponseBody
    public FacResult create(@RequestBody OrderCreateVo order) {
        try {
            OrderCreateRes res = this.orderService.createOrderFromClient(order);
            return FacResult.success(res);
        } catch (FacException fe) {
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            return FacResult.error();
        }
    }

    @PostMapping("/statistics")
    @ResponseBody
    public FacResult statistics(@RequestBody OrderReq req) {
        OrderStatisticsVo statistics = this.orderService.orderStatistics(req.getToken());
        return FacResult.success(statistics);
    }

    @PostMapping("/close")
    @ResponseBody
    public FacResult close(@RequestBody OrderReq req) {
        if (StringUtils.isEmpty(req.getToken()) || StringUtils.isEmpty(req.getOrderId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        this.orderService.closeOrder(req.getToken(), req.getOrderId());
        return FacResult.success("");
    }

    @PostMapping("/detail")
    @ResponseBody
    public FacResult detail(@RequestBody OrderReq req) {
        // 根据订单id和用户token(openid)查询当前指定的订单商品详情
        if (StringUtils.isEmpty(req.getToken()) || StringUtils.isEmpty(req.getId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        OrderDetailVo orderDetailVo = this.orderService.orderDetail(Long.valueOf(req.getId()), req.getToken());
        if (orderDetailVo != null) {
            return FacResult.success(orderDetailVo);
        } else {
            return FacResult.error("当前订单已不存在，请联系管理员");
        }
    }
}
