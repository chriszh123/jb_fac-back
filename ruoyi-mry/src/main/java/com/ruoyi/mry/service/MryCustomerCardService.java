/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerCard
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

/**
 * MryCustomerCard
 * @author zhangguifeng
 * @create 2020-01-17 17:28
 **/
public interface MryCustomerCardService {

    List<MryCustomerCard> selectCustomerCards(MryCustomerCard customerCard);

    int insertMryCustomerCard(MryCustomerCard customerCard);

    MryCustomerCard selectCustomerCardById(Long id);

    int updateMryCustomerCard(MryCustomerCard customerCard);

    int deleteMryCustomerCardByIds(String ids, SysUser user);

    List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard);

    List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard);

    List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard);
}
