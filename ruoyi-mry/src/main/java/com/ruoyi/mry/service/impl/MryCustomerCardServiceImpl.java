/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryCustomerCardService
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MryCustomerCardService
 * @author zhangguifeng
 * @create 2020-01-17 17:29
 **/
@Slf4j
@Service("mryCustomerCardService")
public class MryCustomerCardServiceImpl implements MryCustomerCardService {


    @Override
    public List<MryCustomerCard> selectCustomerCards(MryCustomerCard customerCard) {
        return null;
    }

    @Override
    public int insertMryCustomerCard(MryCustomerCard customerCard) {
        return 0;
    }

    @Override
    public MryCustomerCard selectCustomerCardById(Long id) {
        return null;
    }

    @Override
    public int updateMryCustomerCard(MryCustomerCard customerCard) {
        return 0;
    }

    @Override
    public int deleteMryCustomerCardByIds(String ids, SysUser user) {
        return 0;
    }

    @Override
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        return null;
    }

    @Override
    public List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard) {
        return null;
    }

    @Override
    public List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard) {
        return null;
    }
}
