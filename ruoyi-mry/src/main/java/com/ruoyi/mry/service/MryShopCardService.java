/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryShopCard
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * MryShopCard
 * @author zhangguifeng
 * @create 2020-01-17 18:00
 **/
public interface MryShopCardService {

    List<MryShopCard> selectShopCards(MryShopCard shopCard);

    int insertShopCard(MryShopCard shopCard);

    MryShopCard selectShopCard(Long id);

    int updateShopCard(MryShopCard shopCard);

    int deleteShopCards(String ids, SysUser user);
}
