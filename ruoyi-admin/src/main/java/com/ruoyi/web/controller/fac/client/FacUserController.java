/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.service.IBuyerAddressService;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.client.ShippingAddress;
import com.ruoyi.fac.vo.client.UserAmountVo;
import com.ruoyi.fac.vo.client.UserDetailVo;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/user")
public class FacUserController extends BaseController {

    @Autowired
    private IBuyerService buyerService;
    @Autowired
    private IBuyerAddressService buyerAddressService;

    /**
     * 校验当前用户token
     *
     * @param token
     * @return
     */
    @PostMapping("/check-token")
    @ResponseBody
    public FacResult checkToken(String token) {
        Buyer buyer = this.buyerService.selectBuyerByToken(token);
        if (buyer == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        } else {
            return FacResult.success("");
        }
    }

    @PostMapping("/shipping-address/default")
    @ResponseBody
    public FacResult defaultShippingAddress(String token) {
        ShippingAddress shippingAddress = new ShippingAddress();


        return FacResult.success(shippingAddress);
    }

    @PostMapping("/detail")
    @ResponseBody
    public FacResult detail(String token) {
        UserDetailVo detailVo = new UserDetailVo();

        return FacResult.success(detailVo);
    }

    @PostMapping("/amount")
    @ResponseBody
    public FacResult amount(String token) {
        UserAmountVo amountVo = new UserAmountVo();

        return FacResult.success(amountVo);
    }

    @PostMapping("/shipping-address/list")
    @ResponseBody
    public FacResult shippingAddressList(String token) {
        List<ShippingAddress> shippingAddressList = new ArrayList<>();

        return FacResult.success(shippingAddressList);
    }

    @PostMapping("/shipping-address/detail")
    @ResponseBody
    public FacResult defaultShippingDetail(String token, long id) {
        ShippingAddress shippingAddress = new ShippingAddress();


        return FacResult.success(shippingAddress);
    }

    @PostMapping("/shipping-address/add")
    @ResponseBody
    public FacResult addAddress(ShippingAddress shippingAddress) {

        return FacResult.success("");
    }

    @PostMapping("/shipping-address/update")
    @ResponseBody
    public FacResult updateAddress(ShippingAddress shippingAddress) {

        return FacResult.success("");
    }

    @PostMapping("/shipping-address/delete")
    @ResponseBody
    public FacResult deleteAddress(String token, long id) {

        return FacResult.success("");
    }
}
