package com.ruoyi.fac.cache.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.BuyerCache;
import com.ruoyi.fac.constant.CacheKeys;
import com.ruoyi.fac.mapper.FacBuyerMapper;
import com.ruoyi.fac.model.FacBuyer;
import com.ruoyi.fac.model.FacBuyerExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zgf
 * Date 2019/10/2 15:30
 * Description
 */
@Slf4j
@Service("buyerCache")
public class BuyerCacheImpl implements BuyerCache {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FacBuyerMapper facBuyerMapper;


    @Override
    public FacBuyer getBuyerCache(String token) {
        try {
            String key = String.format(CacheKeys.KEY_FAC_BUYER_TOKEN, token);
            String buyerStr = this.stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(buyerStr)) {
                FacBuyer facBuyer = JSON.parseObject(buyerStr, FacBuyer.class);
                return facBuyer;
            }
            final FacBuyerExample buyerExample = new FacBuyerExample();
            buyerExample.createCriteria().andIsDeletedEqualTo(false).andTokenEqualTo(token);
            List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
            if (CollectionUtils.isNotEmpty(buyers)) {
                FacBuyer facBuyer = buyers.get(0);
                // 设置10天的缓存有效期
                this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(facBuyer), CacheKeys.EXPIRIER_TIME, TimeUnit.DAYS);
                return facBuyer;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public FacBuyer getBuyerCache(Long buyerId) {
        try {
            String key = String.format(CacheKeys.KEY_FAC_BUYER_ID, buyerId);
            String buyerStr = this.stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(buyerStr)) {
                FacBuyer facBuyer = JSON.parseObject(buyerStr, FacBuyer.class);
                return facBuyer;
            }
            final FacBuyerExample buyerExample = new FacBuyerExample();
            buyerExample.createCriteria().andIsDeletedEqualTo(false).andIdEqualTo(buyerId);
            List<FacBuyer> buyers = this.facBuyerMapper.selectByExample(buyerExample);
            if (CollectionUtils.isNotEmpty(buyers)) {
                FacBuyer facBuyer = buyers.get(0);
                // 设置10天的缓存有效期
                this.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(facBuyer), CacheKeys.EXPIRIER_TIME, TimeUnit.DAYS);
                return facBuyer;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    @Override
    public void deleteBuyerCache(String token) {
        String key = String.format(CacheKeys.KEY_FAC_BUYER_TOKEN, token);
        this.stringRedisTemplate.delete(key);
    }

    @Override
    public void deleteBuyerCache(Long buyerId) {
        String key = String.format(CacheKeys.KEY_FAC_BUYER_ID, buyerId);
        this.stringRedisTemplate.delete(key);
    }
}
