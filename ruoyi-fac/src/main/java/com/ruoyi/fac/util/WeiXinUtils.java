package com.ruoyi.fac.util;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.Global;
import com.ruoyi.fac.constant.CacheKeys;
import com.ruoyi.fac.vo.client.AccessToken;
import com.ruoyi.fac.vo.client.SessionDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class WeiXinUtils {

    private final static String REDIS_KEY_ACCESSTOKEN = "weixin:accesstoken";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static WeiXinUtils instance = null;
    private static Object object = new Object();

    public static WeiXinUtils getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new WeiXinUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 获取微信access_token
     *
     * @return
     */
    public String queryWxAccessToken() {
        String accessTokenStr = this.stringRedisTemplate.opsForValue().get(REDIS_KEY_ACCESSTOKEN);
        if (StringUtils.isNotBlank(accessTokenStr)) {
            return accessTokenStr;
        }

        String appid = Global.getFacAppId();
        String secret = Global.getFacSecret();
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(accessTokenUrl, appid, secret))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                // eg:{"access_token":"26_ch8pJUHYZb7uM8hSV2jX0NhjDCj2ltw4j2lu2slpNV4tpVvlEF4s91ycutVaXtVGY_ygG4UayCo6Z2XcfO5OUqgkEuxVGx1pxUKGS5wrdHoXJ5YHpK8ga2AVebn3niaOHVdVtCLd3rMwMECaZLUgAIALXG","expires_in":7200}
                AccessToken accessTokenVo = JSON.parseObject(response.body().string(), AccessToken.class);
                if (accessTokenVo != null && StringUtils.isNotBlank(accessTokenVo.getAccess_token())) {
                    log.info(String.format("----------------[queryWxAccessToken] accessTokenVo : %s", accessTokenVo));
                    // access_token设置2小时的过期时间
                    this.stringRedisTemplate.opsForValue().set(REDIS_KEY_ACCESSTOKEN, accessTokenVo.getAccess_token()
                            , CacheKeys.EXPIRIER_TIME_ACCESS_TOKEN, TimeUnit.SECONDS);

                    return accessTokenVo.getAccess_token();
                } else {
                    log.info(String.format("----------------[queryWxAccessToken] has no AccessToken info, response:%s"
                            , JSON.toJSONString(response)));
                }
            } else {
                log.error(String.format("------[queryWxAccessToken] has no result, msg:%s"
                        , response == null ? "response is null" : JSON.toJSONString(response)));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return "";
    }
}
