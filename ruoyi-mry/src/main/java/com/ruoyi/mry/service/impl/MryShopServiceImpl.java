/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryShopService
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopExample;
import com.ruoyi.mry.service.MryShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MryShopService
 * @author zhangguifeng
 * @create 2020-01-17 18:04
 **/
@Slf4j
@Service("mryShopService")
public class MryShopServiceImpl implements MryShopService {

    @Autowired
    private MryShopMapper shopMapper;

    @Override
    public List<MryShop> selectShops(MryShop shop) {
        final MryShopExample shopExample = new MryShopExample();
        MryShopExample.Criteria criteria = shopExample.createCriteria();
        criteria.andIsDeletedEqualTo(false);
        if (StringUtils.isNotBlank(shop.getName()) && StringUtils.isNotBlank(shop.getName().trim())) {
            criteria.andNameLike("%" + shop.getName().trim() + "%");
        }
        shopExample.setOrderByClause(MryConstant.DEFAULT_ORDER_CLAUSE);

        List<MryShop> shops = this.shopMapper.selectByExample(shopExample);
        return shops;
    }

    @Override
    public int insertShop(MryShop shop) {
        return 0;
    }

    @Override
    public MryShop selectShopById(Long id) {
        return null;
    }

    @Override
    public int updateShop(MryShop shop) throws MryException {
        return 0;
    }

    @Override
    public int deleteShopByIds(String ids) throws MryException {
        return 0;
    }
}
