package com.ruoyi.fac.cache.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.constant.CacheKeys;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.mapper.FacProductMapper;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.model.FacProduct;
import com.ruoyi.fac.model.FacProductExample;
import com.ruoyi.fac.vo.client.AccessToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("productCache")
public class ProductCacheImpl implements ProductCache {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FacProductMapper facProductMapper;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public Product getProductCache(String prodId) {
        try {
            String key = String.format(CacheKeys.KEY_PRODUCT, prodId);
            String productStr = this.stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(productStr)) {
                Product product = JSON.parseObject(productStr, Product.class);
                return product;
            }
            Product product = this.productMapper.selectProductById(Long.valueOf(prodId));
            if (product != null && product.getIsDeleted().intValue() == 0) {
                // 设置10天的缓存有效期
                this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(product), CacheKeys.EXPIRIER_TIME, TimeUnit.DAYS);
                return product;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public FacProduct getFacProductCache(String prodId) {
        try {
            String key = String.format(CacheKeys.KEY_FAC_PRODUCT, prodId);
            String facProductStr = this.stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(facProductStr)) {
                FacProduct facProduct = JSON.parseObject(facProductStr, FacProduct.class);
                return facProduct;
            }
            FacProductExample example = new FacProductExample();
            example.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(Long.valueOf(prodId));
            List<FacProduct> facProducts = this.facProductMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(facProducts)) {
                FacProduct facProduct = facProducts.get(0);
                // 设置10天的缓存有效期
                this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(facProduct), CacheKeys.EXPIRIER_TIME, TimeUnit.DAYS);
                return facProduct;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public void deleteProdCache(String prodId) {
        String key = String.format(CacheKeys.KEY_PRODUCT, prodId);
        this.stringRedisTemplate.delete(key);
    }

    @Override
    public void deleteFacProdCache(String prodId) {
        String key = String.format(CacheKeys.KEY_FAC_PRODUCT, prodId);
        this.stringRedisTemplate.delete(key);
    }

    @Override
    public String getWeixinAccessToken() throws Exception{
        String appid = Global.getFacAppId();
        String key = String.format(CacheKeys.KEY_PRODUCT, appid);
        String accessTokenStr = this.stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(accessTokenStr)) {
            AccessToken accessTokenVo = JSON.parseObject(accessTokenStr, AccessToken.class);
            return accessTokenVo.getAccess_token();
        }

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
                if (accessTokenVo != null && org.apache.commons.lang3.StringUtils.isNotBlank(accessTokenVo.getAccess_token())) {
                    log.info(String.format("----------------[getWeixinAccessToken] accessTokenVo : %s", accessTokenVo));
                    // access_token设置2小时的过期时间
                    this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(accessTokenVo), CacheKeys.EXPIRIER_TIME_ACCESS_TOKEN, TimeUnit.SECONDS);

                    String accessToken = accessTokenVo.getAccess_token();

                    return accessToken;
                } else {
                    log.info(String.format("----------------[getWeixinAccessToken] has no AccessToken info, response:%s"
                            , JSON.toJSONString(response)));
                }
            } else {
                log.error(String.format("------[getWeixinAccessToken] has no result, msg:%s"
                        , response == null ? "response is null" : JSON.toJSONString(response)));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return "";
    }
}
