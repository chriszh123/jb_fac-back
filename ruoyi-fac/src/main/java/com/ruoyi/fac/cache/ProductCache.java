package com.ruoyi.fac.cache;

import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.model.FacProduct;

public interface ProductCache {
    Product getProductCache(String prodId) throws Exception;

    FacProduct getFacProductCache(String prodId) throws Exception;

    void deleteProdCache(String prodId) throws Exception;

    void deleteFacProdCache(String prodId) throws Exception;
}
