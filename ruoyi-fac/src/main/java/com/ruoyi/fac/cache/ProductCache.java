package com.ruoyi.fac.cache;

import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.model.FacProduct;

public interface ProductCache {
    Product getProductCache(String prodId);

    FacProduct getFacProductCache(String prodId);

    void deleteProdCache(String prodId);

    void deleteFacProdCache(String prodId);

    String getWeixinAccessToken();
}
