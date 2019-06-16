package com.ruoyi.fac.service;

import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zgf
 * Date 2019/2/18 17:15
 * Description
 */
public interface IPayService {
    /**
     * 微信支付预支付接口
     *
     * @param req
     * @return
     * @throws Exception
     */
    WxPrePayRes getWxPrePayInfo(WxPrePayReq req, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 微信支付结果通知接口
     *
     * @param request
     * @param response
     */
    String payCallback(HttpServletRequest request, HttpServletResponse response);
}
