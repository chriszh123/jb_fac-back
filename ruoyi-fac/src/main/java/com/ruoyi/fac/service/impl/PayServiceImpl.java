package com.ruoyi.fac.service.impl;

import com.ruoyi.fac.service.IPayService;
import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;
import org.springframework.stereotype.Service;

/**
 * Created by zgf
 * Date 2019/2/18 17:16
 * Description
 */
@Service("payService")
public class PayServiceImpl implements IPayService {
    /**
     * 微信支付预支付接口
     *
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WxPrePayRes getWxPrePayInfo(WxPrePayReq req) throws Exception {
        return null;
    }
}
