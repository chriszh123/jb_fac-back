/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryShop
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.model.MryShop;

import java.util.List;

/**
 * MryShop
 * @author zhangguifeng
 * @create 2020-01-17 18:04
 **/
public interface MryShopService {

    List<MryShop> selectShops(MryShop shop);

    int insertShop(MryShop shop);

    MryShop selectShopById(Long id);

    int updateShop(MryShop shop) throws MryException;

    int deleteShopByIds(String ids) throws MryException;
}
