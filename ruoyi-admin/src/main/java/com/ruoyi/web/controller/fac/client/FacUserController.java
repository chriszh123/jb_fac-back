/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.utils.StringUtils;
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
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/fac/client/user")
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
        if (StringUtils.isEmpty(token)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        UserDetailVo detailVo = this.buyerService.detailUser(token);
        if (detailVo == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(detailVo);
    }

    @PostMapping("/amount")
    @ResponseBody
    public FacResult amount(String token) {
        if (StringUtils.isEmpty(token)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        UserAmountVo amountVo = this.buyerService.userAmount(token);
        if (amountVo == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(amountVo);
    }

    @PostMapping("/shipping-address/list")
    @ResponseBody
    public FacResult shippingAddressList(String token) {
        if (StringUtils.isEmpty(token)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        List<ShippingAddress> shippingAddressList = this.buyerAddressService.shippingAddressList(token);
        if (CollectionUtils.isEmpty(shippingAddressList)) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(shippingAddressList);
    }

    @PostMapping("/shipping-address/detail")
    @ResponseBody
    public FacResult detailShippingAddress(String token, String id) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(id)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        ShippingAddress shippingAddress = this.buyerAddressService.detailShippingAddress(token, id);
        if (shippingAddress == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
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
        if (shippingAddress == null || StringUtils.isEmpty(shippingAddress.getToken()) || shippingAddress.getId() == null) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }

        this.buyerAddressService.updateAddress(shippingAddress);
        return FacResult.success("");
    }

    @PostMapping("/shipping-address/delete")
    @ResponseBody
    public FacResult deleteAddress(String token, String id) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(id)) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        this.buyerAddressService.deleteAddress(token, id);
        return FacResult.success("");
    }
}
