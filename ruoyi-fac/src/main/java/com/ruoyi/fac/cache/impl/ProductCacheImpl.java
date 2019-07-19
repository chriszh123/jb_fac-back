package com.ruoyi.fac.cache.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.cache.ProductCache;
import com.ruoyi.fac.constant.CacheKeys;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.mapper.FacProductMapper;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.model.FacProduct;
import com.ruoyi.fac.model.FacProductExample;
import lombok.extern.slf4j.Slf4j;
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
    StringRedisTemplate stringRedisTemplate;

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
}
