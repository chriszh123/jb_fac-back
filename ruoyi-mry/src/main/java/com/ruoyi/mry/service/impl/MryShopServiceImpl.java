/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: MryShopService
 */
package com.ruoyi.mry.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mry.constant.MryConstant;
import com.ruoyi.mry.enums.ShopStatus;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.mapper.MryShopMapper;
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.model.MryShopExample;
import com.ruoyi.mry.service.MryShopService;
import com.ruoyi.mry.util.MryTimeUtils;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        final Date nowDate = new Date();
        shop.setCreateTime(nowDate);
        shop.setUpdateTime(nowDate);
        shop.setIsDeleted(false);
        shop.setStatus(ShopStatus.STOP.getValue());

        return this.shopMapper.insertSelective(shop);
    }

    @Override
    public MryShop selectShopById(Long id) {
        if (id == null) {
            throw new MryException("主键id不能为空");
        }
        MryShop shop = this.shopMapper.selectByPrimaryKey(id.shortValue());
        if (shop.getOpenTime() != null) {
            shop.setOpenTimeStr(MryTimeUtils.date2Str(shop.getOpenTime(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (shop.getBizTimeStart() != null) {
            shop.setBizTimeStartStr(MryTimeUtils.date2Str(shop.getBizTimeStart(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (shop.getBizTimeEnd() != null) {
            shop.setBizTimeEndStr(MryTimeUtils.date2Str(shop.getBizTimeEnd(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return shop;
    }

    @Override
    public int updateShop(MryShop shop) throws MryException {
        if (shop == null || shop.getId() == null) {
            throw new MryException("主键id不能为空");
        }
        final Date nowDate = new Date();
        shop.setUpdateTime(nowDate);

        if (StringUtils.isNotBlank(shop.getOpenTimeStr())) {
            shop.setOpenTime(MryTimeUtils.parseTime(shop.getOpenTimeStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(shop.getBizTimeStartStr())) {
            shop.setBizTimeStart(MryTimeUtils.parseTime(shop.getBizTimeStartStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (StringUtils.isNotBlank(shop.getBizTimeEndStr())) {
            shop.setBizTimeEnd(MryTimeUtils.parseTime(shop.getBizTimeEndStr(), MryTimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return this.shopMapper.updateByPrimaryKeySelective(shop);
    }

    @Override
    public int deleteShopByIds(String ids, SysUser user) throws MryException {
        if (StringUtils.isBlank(ids)) {
            throw new MryException("主键id不能为空");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        List<Short> idsLongs = new ArrayList<>();
        for (String id : idsList) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            idsLongs.add(Short.valueOf(id));
        }
        if (CollectionUtils.isNotEmpty(idsLongs)) {
            MryShopExample shopExample = new MryShopExample();
            shopExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(idsLongs);
            MryShop update = new MryShop();
            update.setIsDeleted(true);
            update.setUpdateTime(new Date());
            if (user != null) {
                update.setOperatorId(user.getUserId());
                update.setOperatorName(user.getUserName());
            }
            this.shopMapper.updateByExampleSelective(update, shopExample);
        }
        return 1;
    }
}
