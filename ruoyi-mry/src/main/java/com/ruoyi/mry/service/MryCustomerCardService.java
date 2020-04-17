/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerCard
 */
package com.ruoyi.mry.service;

import com.ruoyi.mry.model.*;
import com.ruoyi.system.domain.SysUser;

import java.util.List;
import java.util.Map;

/**
 * MryCustomerCard
 * @author zhangguifeng
 * @create 2020-01-17 17:28
 **/
public interface MryCustomerCardService {

    List<MryCustomerCard> selectCustomerCards(MryCustomerCard customerCard, Map<Long, MryCustomer> customerMap, List<MryShop> shops
            , List<MryServicePro> servicePros);

    int insertMryCustomerCard(MryCustomerCard customerCard);

    MryCustomerCard selectCustomerCardById(Long id);

    int updateMryCustomerCard(MryCustomerCard customerCard);

    int deleteMryCustomerCardByIds(String ids, SysUser user);

    List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard);

    List<MryCustomerCard> getCustomerCardsByShopCustomer(MryCustomerCard customerCard);

    List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard);

    List<MryStaff> getStaffsByShopId(MryCustomerCard customerCard);

    List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard);

    Map<Long, MryCustomer> listCustomers(MryCustomerCard customerCard);
}
