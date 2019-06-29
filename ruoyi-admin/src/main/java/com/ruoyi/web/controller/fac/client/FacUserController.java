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
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.service.IBuyerAddressService;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.fac.service.IUserSignService;
import com.ruoyi.fac.service.WechatAdapterService;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.client.req.OrderReq;
import com.ruoyi.fac.vo.client.req.SignReq;
import com.ruoyi.fac.vo.client.req.UserInfo;
import com.ruoyi.fac.vo.client.req.UserReq;
import com.ruoyi.fac.vo.client.res.LoginVo;
import com.ruoyi.fac.vo.client.res.UserScoreLogs;
import com.ruoyi.fac.vo.client.res.UserSignLogs;
import com.ruoyi.framework.web.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(FacUserController.class);

    @Autowired
    private IBuyerService buyerService;
    @Autowired
    private IBuyerAddressService buyerAddressService;
    @Autowired
    private WechatAdapterService wechatAdapterService;
    @Autowired
    private IUserSignService userSignService;

    /**
     * 校验当前用户token
     *
     * @param req
     * @return
     */
    @PostMapping("/check-token")
    @ResponseBody
    public FacResult checkToken(@RequestBody UserReq req) {
        Buyer buyer = this.buyerService.selectBuyerByToken(req.getToken());
        if (buyer == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        } else {
            return FacResult.success("");
        }
    }

    @PostMapping("/shipping-address/default")
    @ResponseBody
    public FacResult defaultShippingAddress(@RequestBody UserReq req) {
        ShippingAddress shippingAddress = new ShippingAddress();


        return FacResult.success(shippingAddress);
    }

    @PostMapping("/detail")
    @ResponseBody
    public FacResult detail(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getToken())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        UserDetailVo detailVo = this.buyerService.detailUser(req.getToken());
        if (detailVo == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(detailVo);
    }

    @PostMapping("/amount")
    @ResponseBody
    public FacResult amount(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getToken())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        UserAmountVo amountVo = this.buyerService.userAmount(req.getToken());
        if (amountVo == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(amountVo);
    }

    @PostMapping("/shipping-address/list")
    @ResponseBody
    public FacResult shippingAddressList(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getToken())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        List<ShippingAddress> shippingAddressList = this.buyerAddressService.shippingAddressList(req.getToken());
        if (CollectionUtils.isEmpty(shippingAddressList)) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(shippingAddressList);
    }

    @PostMapping("/shipping-address/detail")
    @ResponseBody
    public FacResult detailShippingAddress(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getToken()) || StringUtils.isEmpty(req.getId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        ShippingAddress shippingAddress = this.buyerAddressService.detailShippingAddress(req.getToken(), req.getId());
        if (shippingAddress == null) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
        return FacResult.success(shippingAddress);
    }

    @PostMapping("/shipping-address/add")
    @ResponseBody
    public FacResult addAddress(@RequestBody ShippingAddress shippingAddress) {
        if (shippingAddress == null || StringUtils.isEmpty(shippingAddress.getToken())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        try {
            Long id = this.buyerAddressService.addAddress(shippingAddress);
            return FacResult.success(id);
        } catch (FacException fe) {
            return FacResult.error(fe.getMessage());
        } catch (Exception fe) {
            return FacResult.error("");
        }
    }

    @PostMapping("/shipping-address/update")
    @ResponseBody
    public FacResult updateAddress(@RequestBody ShippingAddress shippingAddress) {
        if (shippingAddress == null || StringUtils.isEmpty(shippingAddress.getToken()) || shippingAddress.getId() == null) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }

        this.buyerAddressService.updateAddress(shippingAddress);
        return FacResult.success("");
    }

    @PostMapping("/shipping-address/delete")
    @ResponseBody
    public FacResult deleteAddress(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getToken()) || StringUtils.isEmpty(req.getId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        this.buyerAddressService.deleteAddress(req.getToken(), req.getId());
        return FacResult.success("");
    }

    @PostMapping("/wxapp/login")
    @ResponseBody
    public FacResult wxappLogin(@RequestBody UserReq req) {
        if (StringUtils.isEmpty(req.getCode())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }

        try {
            SessionDTO sessionDTO = this.wechatAdapterService.jscode2session(req.getCode());
            if (sessionDTO == null || StringUtils.isBlank(sessionDTO.getOpenid())) {
                return FacResult.error(FacCode.UNREGISTER.getCode(), FacCode.UNREGISTER.getMsg());
            }
            Long buyerId = this.buyerService.saveBuyer(sessionDTO.getOpenid(), req.getCode());
            LoginVo loginVo = new LoginVo();
            loginVo.setOpenid(sessionDTO.getOpenid());
            // 这里token暂时与openId值相同，代表用户唯一标识
            loginVo.setToken(sessionDTO.getOpenid());
            loginVo.setUid(buyerId);

            return FacResult.success(loginVo);
        } catch (Exception ex) {
            LOGGER.error("[wxappLogin] error", ex);
            return FacResult.error(FacCode.ERROR_SERVER_INTERVAL.getCode(), FacCode.ERROR_SERVER_INTERVAL.getMsg());
        }
    }

    @PostMapping("/wxapp/register/complex")
    @ResponseBody
    public FacResult wxappRegister(@RequestBody UserReq req) {
        return FacResult.success("");
    }

    @PostMapping("/updateUserInfo")
    @ResponseBody
    public FacResult updateUserInfo(@RequestBody UserInfo userInfo) {
        String result = this.buyerService.updateUserInfo(userInfo);
        if (StringUtils.isBlank(result)) {
            return FacResult.success("");
        } else {
            return FacResult.error(result);
        }
    }

    @PostMapping("/sign")
    @ResponseBody
    public FacResult sign(@RequestBody SignReq req) {
        try {
            int signPoint = this.userSignService.sign(req);
            return FacResult.success(signPoint);
        } catch (FacException fe) {
            LOGGER.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }

    @PostMapping("/sign/logs")
    @ResponseBody
    public FacResult signLogs(@RequestBody SignReq req) {
        try {
            UserSignLogs logs = this.userSignService.queryUserSignLogs(req);
            return FacResult.success(logs);
        } catch (FacException fe) {
            LOGGER.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }

    @PostMapping("/score/logs")
    @ResponseBody
    public FacResult signScoreLogs(@RequestBody SignReq req) {
        try {
            UserScoreLogs logs = this.userSignService.queryUserScoreLogs(req);
            return FacResult.success(logs);
        } catch (FacException fe) {
            LOGGER.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }

    @PostMapping("/amount/logs")
    @ResponseBody
    public FacResult consumerLogs(@RequestBody SignReq req) {
        try {
            UserScoreLogs logs = this.userSignService.queryUserConsumerLogs(req);
            return FacResult.success(logs);
        } catch (FacException fe) {
            LOGGER.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }

    @PostMapping("/userInfo")
    @ResponseBody
    public FacResult userInfo(@RequestBody OrderReq req) {
        if (req == null || StringUtils.isBlank(req.getToken())) {
            return FacResult.error("用户token不能为空");
        }
        UserBaseVo vo = this.buyerService.getUserInfo(req.getToken());
        return FacResult.success(vo);
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");
        String s = df.format((float)2/100);
        System.out.println(s);
    }
}
