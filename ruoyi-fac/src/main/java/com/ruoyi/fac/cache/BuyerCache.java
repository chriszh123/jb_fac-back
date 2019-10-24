package com.ruoyi.fac.cache;

import com.ruoyi.fac.model.FacBuyer;

/**
 * Created by zgf
 * Date 2019/10/2 15:28
 * Description
 */
public interface BuyerCache {
    FacBuyer getBuyerCache(String token);

    FacBuyer getBuyerCache(Long buyerId);

    void deleteBuyerCache(String token);

    void deleteBuyerCache(Long buyerId);
}
