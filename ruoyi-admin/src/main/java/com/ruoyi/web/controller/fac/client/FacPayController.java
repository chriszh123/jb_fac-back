package com.ruoyi.web.controller.fac.client;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.service.IPayService;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;
import com.ruoyi.framework.web.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信预支付接口
 * Date 2019/2/18 17:07
 * Description
 */
@Controller
@RequestMapping("/fac/client/pay")
public class FacPayController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FacPayController.class);

    @Autowired
    private IPayService payService;

    /**
     * 微信支付与预支付接口
     *
     * @param req
     * @return
     */
    @PostMapping("/wx/wxapp")
    @ResponseBody
    public FacResult wxPay(@RequestBody WxPrePayReq req, HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信 统一下单 接口调用");
        try {
            if (req == null || StringUtils.isBlank(req.getToken()) || StringUtils.isBlank(req.getMoney())
                    || req.getNextAction() == null || StringUtils.isBlank(req.getNextAction().getId())) {
                return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
            }
            logger.info(String.format("============================wxPay start：%s", JSON.toJSONString(req)));
            WxPrePayRes res = this.payService.getWxPrePayInfo(req, request, response);
            logger.info(String.format("==================================wxPay success：%s", JSON.toJSONString(res)));
            return FacResult.success(res);
        } catch (Exception ex) {
            logger.error("================wxPay error", ex);
            return FacResult.error(ex.getMessage());
        }
    }

    @PostMapping("/wx/payCallback")
    @ResponseBody
    public void payCallback(HttpServletRequest request, HttpServletResponse response) {
        // 通知地址 回调服务器 支付结果(这个回调 如果不返回给微信服务器 是否成功回调标示 则会一直回调8次 一直到返回成功标示位置)
        try {
            logger.info("=================================payCallback start.=====================");
            this.payService.payCallback(request, response);
            logger.info("========================payCallback success.==============================");
        } catch (Exception ex) {
            logger.error("[payCallback] error", ex);
        }
    }
}
