package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.service.IPayService;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.wxpay.WxPrePayReq;
import com.ruoyi.fac.vo.wxpay.WxPrePayRes;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信预支付接口
 * Date 2019/2/18 17:07
 * Description
 */
@Controller
@RequestMapping("/fac/client/pay")
public class FacPayController extends BaseController {

    @Autowired
    private IPayService payService;

    /**
     * 微信支付与支付接口
     *
     * @param req
     * @return
     */
    @PostMapping("/wx/wxapp")
    @ResponseBody
    public FacResult wxPay(@RequestBody WxPrePayReq req) {
        try {
            WxPrePayRes res = this.payService.getWxPrePayInfo(req);
            return FacResult.success(res);
        } catch (Exception ex) {
            return FacResult.error(ex.getMessage());
        }
    }
}
