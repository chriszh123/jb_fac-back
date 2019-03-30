package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.Global;
import com.ruoyi.fac.service.WechatAdapterService;
import com.ruoyi.fac.util.HttpsGetUtil;
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

//                this.getUserInfoFromWx(code);

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

    private void getUserInfoFromWx(String code) {
        // 这里每一个code不能连续作为调用wx接口的参数，可以需要重新写一个获取用户信息的接口了，其中code由前端自己调用wx.login()取获取新的code,
        // 再来调用微信接口获取用户信息
        String appid = Global.getFacAppId();
        String secret = Global.getFacSecret();

        //拼接
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="
                + appid
                + "&secret="
                + secret
                + "&code=CODE&grant_type=authorization_code";
        String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        get_access_token_url = get_access_token_url.replace("CODE", code);
        String json = HttpsGetUtil.doHttpsGetJson(get_access_token_url);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
        get_userinfo = get_userinfo.replace("OPENID", openid);
        String userInfoJson = HttpsGetUtil.doHttpsGetJson(get_userinfo);
        JSONObject userInfoJO = JSONObject.parseObject(userInfoJson);
        String user_openid = userInfoJO.getString("openid");
        String user_nickname = userInfoJO.getString("nickname");
        String user_sex = userInfoJO.getString("sex");
        String user_province = userInfoJO.getString("province");
        String user_city = userInfoJO.getString("city");
        String user_country = userInfoJO.getString("country");
        String user_headimgurl = userInfoJO.getString("headimgurl");

        logger.info(String.format("user_openid=%s;user_nickname=%s;user_sex=%s;user_province=%s;user_city=%s;user_country=%s;user_headimgurl=%s;",
                user_openid, user_nickname, user_sex, user_province, user_city, user_country, user_headimgurl));
    }
}
