package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.Global;
import com.ruoyi.fac.service.WechatAdapterService;
import com.ruoyi.fac.vo.client.SessionDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("wechatAdapterService")
public class WechatAdapterServiceImpl implements WechatAdapterService {
    private final Logger logger = LoggerFactory.getLogger(WechatAdapterServiceImpl.class);

    @Override
    public SessionDTO jscode2session(String code) {
        String appid = Global.getFacAppId();
        String secret = Global.getFacSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, appid, secret, code))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                SessionDTO sessionDTO = JSON.parseObject(response.body().string(), SessionDTO.class);
                logger.info("jscode2session get url -> {}, info -> {}", String.format(url, appid, secret, code), JSON.toJSONString(sessionDTO));
                return sessionDTO;
            } else {
                logger.error("jscode2session authorize error -> {}", code);
//                throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
            }
        } catch (Exception ex) {
            logger.error("jscode2session authorize error -> {}", code, ex);
//            throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
        }
        return null;
    }
}
