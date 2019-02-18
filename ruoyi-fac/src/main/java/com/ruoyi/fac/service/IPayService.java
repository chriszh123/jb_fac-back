package com.ruoyi.fac.service;

import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;

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
    WxPrePayRes getWxPrePayInfo(WxPrePayReq req) throws Exception;
}
