package com.ruoyi.fac.service;

import com.ruoyi.fac.vo.client.req.WxaQrcodeReq;
import com.ruoyi.fac.vo.client.res.WxaQrcodeVo;

public interface WeiXinService {

    WxaQrcodeVo createPoster(WxaQrcodeReq req) throws Exception;
}
